package it.gennystabile.ricettariobe.config;

import it.gennystabile.ricettariobe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permette di usare @PreAuthorize nei Controller
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    //Espone l'AuthenticationManager come Bean, necessario per richiamare la logica
    // di login standard all'interno dell'AuthController.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        // 1. In Spring Security 7 il costruttore vuoto non esiste più.
        // Lo UserDetailsService DEVE essere passato obbligatoriamente nel costruttore.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userService);

        // 2. Il metodo setUserDetailsService non esiste più,
        // quindi ci limitiamo a settare solo il PasswordEncoder.
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    // Metodo centrale per definire la catena dei filtri HTTP e le regole di accesso
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) {
        http
                //Abilitazione della configurazione CORS personalizzata
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) //Disabilito la protezione CSRF per far funzionare le chiamate POST, PUT, DELETE nelle API REST.
                //Imposto l'applicativo come Stateless (Senza stato)-> ogni chiamata dovrà avere il suo Token".
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Definisco chi può fare cosa per gli endpoint URL
                .authorizeHttpRequests(auth -> auth
                        // Rotte pubbliche: tutti possono registrarsi o vedere Swagger
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/swagger-ui/index.html/**", "/v3/api-docs/**").permitAll()
                        //venendo prima della successiva, ha la priorità
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        //tutto il resto delle chiamate sono limitate
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        //Rotte protette da ruolo: solo chi ha il ruolo "ADMIN" può cancellare un utente
                        // .requestMatchers("/users/deleteUser").hasRole("ADMIN")
                        // Rotta generica: per qualsiasi altra API non elencata sopra, bisogna essere almeno loggato
                        .anyRequest().authenticated()
                )
                // Comunica a Spring Security di usare il provider personalizzato
                .authenticationProvider(authProvider)
                // INSERIMENTO CRITICO: Esegue il filtro JWT prima di quello standard per login via form
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Consente le richieste provenienti dall'applicazione Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Consente i metodi HTTP necessari
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Consente gli header standard e l'header Authorization per il token JWT
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Permette l'invio di credenziali se necessario (inclusi i cookie HttpOnly)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applica le regole a tutti i percorsi dell'applicazione
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
