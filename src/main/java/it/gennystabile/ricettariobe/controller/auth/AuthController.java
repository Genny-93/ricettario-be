package it.gennystabile.ricettariobe.controller.auth;


import it.gennystabile.ricettariobe.dto.auth.LoginRequest;
import it.gennystabile.ricettariobe.dto.auth.ResetPasswordRequest;
import it.gennystabile.ricettariobe.service.JwtService;
import it.gennystabile.ricettariobe.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static it.gennystabile.ricettariobe.utils.constant.ControllersConstants.REQUEST_MAPPING_AUTH;

@RestController
@RequestMapping(REQUEST_MAPPING_AUTH)
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

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

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> generateTokenToResetPassword(@Email @RequestParam String email){
        return ResponseEntity.ok(userService.generateTokenToResetPassword(email));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid ResetPasswordRequest request){
        return ResponseEntity.ok(userService.resetPassword(request));
    }
}
