package it.gennystabile.ricettario_be.controller.auth;


import it.gennystabile.ricettario_be.dto.loginDto.LoginRequest;
import it.gennystabile.ricettario_be.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.gennystabile.ricettario_be.utils.constant.controller.UsersCostanti.REQUEST_MAPPING_AUTH;

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
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String jwtToken = jwtService.generateToken(request.getUsername());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return ResponseEntity.ok(jwtToken);
    }
}
