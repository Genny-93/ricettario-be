package it.gennystabile.ricettariobe.controller.utenti;


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
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }


    @GetMapping("{id}")
    public ResponseEntity<UserOutputDto> getById(@PathVariable Long id) {
        UserOutputDto userOutputDto = userService.getById(id);
        if (userOutputDto == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(userOutputDto);
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> register(@Valid @RequestBody UserInputDto userInputDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userInputDto));
    }

    @PutMapping
    public ResponseEntity<String> modififyRole(@NotNull @RequestParam Long id,
                                               @RequestParam(required = true) Role role) {
        return ResponseEntity.ok(userService.modifyRole(id, role));
    }

    @DeleteMapping
    public ResponseEntity<UserOutputDto> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }
}
