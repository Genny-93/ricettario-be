package it.gennystabile.ricettariobe.config;

import it.gennystabile.ricettariobe.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Metodo principale del filtro, eseguito esattamente una volta per ogni richiesta HTTP
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
// 1. Lettura dell'header "Authorization" dalla richiesta HTTP
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. Controllo della presenza e della correttezza formale dell'header
        // Lo standard prevede che il token sia preceduto dalla parola "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Se il token è assente o malformato, la richiesta passa direttamente al filtro successivo.
            // Poiché non viene impostato il SecurityContext, se l'endpoint richiesto
            // necessita di autenticazione, Spring Security restituirà un errore 403 (Forbidden).
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Estrazione della stringa JWT pura
        // Si escludono i primi 7 caratteri corrispondenti a "Bearer "
        jwt = authHeader.substring(7);

        // 4. Estrazione dell'identificativo dell'utente (es. username) decodificando il token
        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Se il token è scaduto o alterato, l'eccezione viene catturata.
            // Si interrompe il processo di autorizzazione e si passa al filtro successivo (che negherà l'accesso).
            filterChain.doFilter(request, response);
            return;
        }
        // 5. Verifica che l'username sia stato estratto con successo e che la richiesta
        // non sia già stata autenticata precedentemente nel contesto corrente
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            // 6. Verifica aggiuntiva per assicurarsi che il token non sia scaduto
            if (jwtService.isTokenValid(jwt)) {

                // Estrae la lista dei ruoli memorizzata all'interno dei claims del JWT durante la fase di login
                List<String> roles = jwtService.extractClaim(jwt, claims -> claims.get("roles", List.class));

                // Converte le stringhe testuali in oggetti SimpleGrantedAuthority riconosciuti da Spring Security
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                // Crea un oggetto User (implementazione di UserDetails) in memoria, contenente nome e ruoli.
                // La password è vuota in quanto l'identità è già certificata dal token stesso.
                UserDetails principal = new User(username, "", authorities);

                // 7. Creazione dell'oggetto Authentication ufficiale riconosciuto da Spring Security
                // I parametri richiesti sono: l'utente (principal), le credenziali (qui null poiché gestite dal token)
                // e la lista dei ruoli/permessi dell'utente
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        authorities
                );

                // Vengono allegati ulteriori dettagli tecnici alla richiesta (es. indirizzo IP del client, session ID)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. Registrazione dell'autenticazione nel contesto di sicurezza
                // Da questo momento esatto, l'utente è considerato autenticato per l'intero ciclo di vita della richiesta
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. Conclusione delle operazioni del filtro e prosecuzione lungo la catena di Spring Security
        filterChain.doFilter(request, response);
    }
}
