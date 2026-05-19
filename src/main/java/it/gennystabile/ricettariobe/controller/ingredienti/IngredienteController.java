package it.gennystabile.ricettariobe.controller.ingredienti;

import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.service.IngredienteService;
import it.gennystabile.ricettariobe.utils.constant.controller.ControllersConstants;
import it.gennystabile.ricettariobe.utils.constant.controller.SecurityConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllersConstants.REQUEST_MAPPING_INGREDIENTI)
@PreAuthorize(SecurityConstants.ALL_PROFILES)
public class IngredienteController {

    private final IngredienteService ingredienteService;


    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }


    @GetMapping
    public ResponseEntity<List<IngredienteOutputDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredienteService.getAllIngredients());
    }

    @GetMapping("{name}")
    public ResponseEntity<IngredienteOutputDto> getIngredientByName(@PathVariable String name) {
        return ResponseEntity.ok(ingredienteService.getIngredientByName(name));
    }

    @PostMapping
    public ResponseEntity<IngredienteOutputDto> postIngredient(@RequestBody IngredienteInputDto inputDto) {
        return ResponseEntity.ok(ingredienteService.postIngredient(inputDto));
    }
}
