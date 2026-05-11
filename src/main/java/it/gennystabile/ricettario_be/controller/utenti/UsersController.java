package it.gennystabile.ricettario_be.controller.utenti;


import it.gennystabile.ricettario_be.dto.userDto.UserInputDto;
import it.gennystabile.ricettario_be.dto.userDto.UserOutputDto;
import it.gennystabile.ricettario_be.service.UserService;
import it.gennystabile.ricettario_be.utils.constant.controller.UsersCostanti;
import it.gennystabile.ricettario_be.utils.enumeration.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(UsersCostanti.REQUEST_MAPPING_UTENTI)
public class UsersController {


    private final UserService userService;

    public UsersController(UserService userService) {
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
    public ResponseEntity<UserOutputDto> register(@RequestBody UserInputDto userInputDto) {
        return ResponseEntity.ok(userService.register(userInputDto));
    }

    @PutMapping
    public ResponseEntity<String> modififyRole(@RequestParam Long id, @RequestParam Role role) {
        return ResponseEntity.ok(userService.modifyRole(id, role));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserOutputDto> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }
}
