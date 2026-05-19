package it.gennystabile.ricettariobe.controller.auth;


import it.gennystabile.ricettariobe.dto.login.LoginRequest;
import it.gennystabile.ricettariobe.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.gennystabile.ricettariobe.utils.constant.controller.ControllersConstants.REQUEST_MAPPING_AUTH;

@RestController
@RequestMapping(REQUEST_MAPPING_AUTH)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //TODO far restituire un oggetto
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {

        // L'AuthenticationManager riceve username e password.
        // Se le credenziali sono corrette, l'autenticazione va a buon fine, recuperando automaticamente l'utente dal database.
        // Se errate, lancia un'eccezione (BadCredentialsException) che restituisce errore 401 al client.
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        // Dal risultato dell'autenticazione viene estratto l'oggetto UserDetails,
        // che contiene i dettagli completi dell'utente appena verificato (compresi i ruoli).
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Viene richiamato il JwtService per generare la stringa del token, passando i dettagli completi.
        String jwtToken = jwtService.generateToken(userDetails);

        // 1. Accede al magazzino globale della sicurezza (SecurityContextHolder)
// 2. Recupera il contenitore dei dati dell'utente corrente (getContext())
// 3. Inietta l'oggetto 'auth' che contiene identità e ruoli (setAuthentication(auth))
        SecurityContextHolder.getContext().setAuthentication(auth);

        return ResponseEntity.ok(jwtToken);
    }
}
