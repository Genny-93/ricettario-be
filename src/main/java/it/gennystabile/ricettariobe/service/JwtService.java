package it.gennystabile.ricettariobe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // Estrae l'username dal token
    public String extractUsername(String token) {
        // Richiama extractClaim passando il riferimento al metodo getSubject della classe Claims
        return extractClaim(token, Claims::getSubject);
    }

    //Metodo generico per estrarre una singola informazione (claim) dal token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Estrae tutti i claims decodificando il token
        final Claims claims = extractAllClaims(token);
        // Applica la funzione desiderata (es. leggere la scadenza o un campo custom)
        return claimsResolver.apply(claims);
    }

    // Genera un token usando solo l'username
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Si estraggono i ruoli dall'oggetto UserDetails.
        // getAuthorities() restituisce una collezione di oggetti GrantedAuthority.
        // Lo stream mappa ogni oggetto estraendo la stringa del ruolo (es. "ROLE_USER") e li converte in una Lista.
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // La lista dei ruoli viene inserita nella mappa con la chiave "roles"
        claims.put("roles", roles);
            // Inizia la costruzione fluida (builder) del token JWT
            return Jwts.builder()
                    // Aggiunge la mappa dei claims personalizzati (i ruoli)
                    .claims(claims) // Eventuali dati aggiuntivi
                    .subject(userDetails.getUsername()) // Il soggetto del token (solitamente l'username)
                    .issuedAt(new Date(System.currentTimeMillis())) // Data di creazione
                    .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Scadenza (es. 24 ore)
                    .signWith(getSignInKey()) // Firma il token con la chiave segreta
                    .compact(); // Compatta il tutto in una singola stringa codificata in Base64Url
        }

    // Verifica che il token sia formalmente valido, controllando che non sia scaduto.
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        // Restituisce true se la data di scadenza NON è antecedente alla data attuale
        return extractExpiration(token).before(new Date());
    }

    //Estrae la data di scadenza dal token.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Decodifica il token, ne verifica la firma e restituisce il corpo (Payload/Claims).
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                // Fornisce la chiave per verificare che il token sia stato generato da questo server
                .verifyWith(getSignInKey())
                .build()
                // Esegue il parsing. Se la firma è errata o il token è manomesso, viene lanciata un'eccezione
                .parseSignedClaims(token)
                .getPayload();
    }

    // Ottiene la chiave crittografica a partire dalla stringa segreta
    private SecretKey getSignInKey() {
        // Decodifica la stringa in un array di byte
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Genera la chiave crittografica compatibile con la libreria jjwt
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
