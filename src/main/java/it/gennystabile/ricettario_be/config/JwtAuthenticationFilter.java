package it.gennystabile.ricettario_be.config;

import it.gennystabile.ricettario_be.service.JwtService;
import it.gennystabile.ricettario_be.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;


    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }


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

        // 4. Estrazione dell'identificativo dell'utente (es. username o email) decodificando il token
        username = jwtService.extractUsername(jwt);

        // 5. Verifica che l'username sia stato estratto con successo e che la richiesta
        // non sia già stata autenticata precedentemente nel contesto corrente
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Recupero dei dettagli completi dell'utente dal database
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            // 6. Validazione del token (verifica della firma e della data di scadenza)
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 7. Creazione dell'oggetto Authentication ufficiale riconosciuto da Spring Security
                // I parametri richiesti sono: l'utente (principal), le credenziali (qui null poiché gestite dal token)
                // e la lista dei ruoli/permessi dell'utente
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
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
