package it.gennystabile.ricettariobe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
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
    private String secretKeyString;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // due contenitori VUOTI (null)
    private SecretKey secretKey;
    private JwtParser jwtParser;

    // Apre il metodo per preparare la chiave solo al primo utilizzo
    private void inizializzazione() {
        // Controlla: se il lettore è vuoto, allora entra nel blocco
        if (this.jwtParser == null) {
            // Trasforma il testo segreto in un array di byte
            byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
            // Genera la chiave crittografica vera e propria dai byte
            this.secretKey = Keys.hmacShaKeyFor(keyBytes);
            //configurazione del lettore di token
            this.jwtParser = Jwts.parser()
                    // Viene indicata la chiave per controllare se i token sono veri
                    .verifyWith(this.secretKey)
                    .build(); // Costruisce definitivamente l'oggetto lettore e lo salva nella variabile
        }
    }

    // Metodo per estrarre l'username dal token
    public String extractUsername(String token) {
        // Richiama extractClaim passando il riferimento al metodo getSubject della classe Claims
        return extractClaim(token, Claims::getSubject);
    }

    // Metodo generico per estrarre una qualsiasi singola informazione (claim) dal token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Estrae tutti i claims decodificando il token
        final Claims claims = extractAllClaims(token);
        // Applica la funzione desiderata (es. leggere la scadenza o un campo custom).
        //così facendo si estrae l'informazione precisa necessaria
        return claimsResolver.apply(claims);
    }

    // Metodo che genera un token usando solo l'username
    public String generateToken(UserDetails userDetails) {
        inizializzazione(); // Si assicura che la chiave e il parser siano pronti prima di procedere
        // Crea una mappa vuota per metterci dentro i dati personalizzati del token
        Map<String, Object> claims = new HashMap<>();

        // Si estraggono i ruoli dall'oggetto UserDetails.
        // getAuthorities() restituisce una collezione di oggetti GrantedAuthority.
        // Lo stream mappa ogni oggetto estraendo la stringa del ruolo (es. "ROLE_USER") e li converte in una Lista.
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // La lista dei ruoli viene inserita nella mappa con la chiave "roles"
        claims.put("roles", roles);
        // Inizia la costruzione (builder) del token JWT
        return Jwts.builder()
                // Inserisce nel token la mappa con i ruoli dell'utente
                .claims(claims)
                // Scrive il nome dell'utente come proprietario del token
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis())) // Data e ora di creazione del token
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Scadenza (es. 24 ore)
                .signWith(secretKey) // Firma digitalmente il token usando la chiave segreta per evitare manomissioni
                .compact(); // Compatta il tutto in una singola stringa codificata in Base64Url
    }

    // Verifica che il token sia formalmente valido, controllando che non sia scaduto.
    public boolean isTokenValid(String token) {
        try {// Avvia un try per intercettare eventuali errori
            return !isTokenExpired(token); // Controlla se il token NON è scaduto e restituisce vero o falso
        } catch (Exception e) {
            return false;
        }
    }

    // Metodo interno per verificare la scadenza del token
    private boolean isTokenExpired(String token) {
        // Restituisce true se la data di scadenza NON è antecedente alla data attuale
        return extractExpiration(token).before(new Date());
    }

    // Metodo interno prt estrarre solo la data di scadenza dal token.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Metodo interno per smontare il token
    private Claims extractAllClaims(String token) {
        inizializzazione(); // Si assicura che il lettore sia configurato prima di leggere il token
        // Prende il lettore riutilizzabile salvato in memoria
        return jwtParser
                // Smonta il token stringa controllando che la firma sia corretta
                .parseSignedClaims(token)
                // Estrae il contenuto informativo interno (i Claims) e lo restituisce
                .getPayload();
    }
    /* Ottiene la chiave crittografica a partire dalla stringa segreta
    private SecretKey getSignInKey() {
        // Decodifica la stringa in un array di byte
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Genera la chiave crittografica compatibile con la libreria jjwt
        return Keys.hmacShaKeyFor(keyBytes);
    }*/
}
