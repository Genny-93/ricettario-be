package it.gennystabile.ricettariobe.controller.utenti;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gennystabile.ricettariobe.dto.user.UserInputDto;
import it.gennystabile.ricettariobe.dto.user.UserOutputDto;
import it.gennystabile.ricettariobe.service.UserService;
import it.gennystabile.ricettariobe.utils.constant.ControllersConstants;
import it.gennystabile.ricettariobe.utils.enumeration.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(ControllersConstants.REQUEST_MAPPING_UTENTI)
@Validated
@Tag(name = "Utenti", description = "Endpoint per il monitoraggio, la registrazione, la modifica dei ruoli e la cancellazione degli utenti")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(
            summary = "Recupera tutti gli utenti. Solo per ADMIN",
            description = "Restituisce l'elenco completo di tutti gli utenti registrati all'interno del sistema."
    )
    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }


    @Operation(
            summary = "Recupera un utente tramite ID. Solo per ADMIN",
            description = "Esegue la ricerca di uno specifico utente sulla base dell'identificativo numerico fornito nell'URL. In assenza di corrispondenze, restituisce uno stato 204 (No Content)."
    )
    @GetMapping("{id}")
    public ResponseEntity<UserOutputDto> findById(@PathVariable Long id) {
        UserOutputDto userOutputDto = userService.getById(id);
        if (userOutputDto == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(userOutputDto);
    }


    @Operation(
            summary = "Registra un nuovo utente",
            description = "Crea un nuovo profilo utente nel sistema elaborando le informazioni fornite nel corpo della richiesta. Restituisce uno stato HTTP 201 (Created) in caso di successo."
    )
    @PostMapping("/register")
    public ResponseEntity<UserOutputDto> register(@Valid @RequestBody UserInputDto userInputDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userInputDto));
    }

    @Operation(
            summary = "Modifica il ruolo di un utente. Solo per ADMIN",
            description = "Aggiorna i permessi di accesso di un utente esistente assegnando il nuovo ruolo specificato tramite i parametri della richiesta."
    )
    @PutMapping
    public ResponseEntity<String> modifyRole(@NotNull @RequestParam Long id,
                                               @RequestParam(required = true) Role role) {
        return ResponseEntity.ok(userService.modifyRole(id, role));
    }

    @Operation(
            summary = "Elimina un utente tramite ID. Solo per ADMIN",
            description = "Rimuove definitivamente un utente dal sistema identificandolo attraverso l'ID numerico fornito come parametro di query."
    )
    @DeleteMapping
    public ResponseEntity<UserOutputDto> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }
}
