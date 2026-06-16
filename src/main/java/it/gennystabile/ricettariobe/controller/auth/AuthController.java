package it.gennystabile.ricettariobe.controller.auth;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gennystabile.ricettariobe.dto.auth.LoginRequest;
import it.gennystabile.ricettariobe.dto.auth.ResetPasswordRequest;
import it.gennystabile.ricettariobe.dto.user.UserInputDto;
import it.gennystabile.ricettariobe.service.JwtService;
import it.gennystabile.ricettariobe.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
@Tag(name = "Autenticazione - AuthController", description = "Endpoint dedicati alla gestione dell'accesso e al recupero delle credenziali")
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

    @Operation(
            summary = "Esegue il login dell'utente",
            description = "Verifica le credenziali fornite nel corpo della richiesta. Se l'autenticazione ha esito positivo, restituisce un token JWT valido."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

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

        ResponseCookie springCookie = ResponseCookie.from("auth_token", jwtToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body("Autenticazione completata con successo");
    }

    @Operation(
            summary = "Richiede il ripristino della password",
            description = "Genera un token univoco di reset associato all'indirizzo email fornito, da utilizzare per la successiva modifica della password."
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<String> generateTokenToResetPassword(@Email @RequestParam String email) {
        return ResponseEntity.ok(userService.generateTokenToResetPassword(email));
    }

    @Operation(
            summary = "Effettua il reset della password",
            description = "Aggiorna la password dell'utente nel sistema utilizzando il token di ripristino precedentemente generato per verificarne l'identità."
    )
    @PutMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie responseCookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body("Logout effettuato");
    }

    /*@Operation(
            summary = "Modifica la password dell'utente",
            description = "Aggiorna la credenziale di accesso del profilo utente elaborando i nuovi dati trasmessi nel corpo della richiesta."
    )

   @PutMapping("/modify-password")
    public ResponseEntity<String> modifyPassword(@Valid @RequestBody UserInputDto inputDto) {
        return ResponseEntity.ok(userService.modifyPassword(inputDto));
    }*/
}
