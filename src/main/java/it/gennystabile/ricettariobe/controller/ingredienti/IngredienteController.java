package it.gennystabile.ricettariobe.controller.ingredienti;

import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteOutputDto;
import it.gennystabile.ricettariobe.service.IngredienteService;
import it.gennystabile.ricettariobe.utils.constant.ControllersConstants;
import it.gennystabile.ricettariobe.utils.constant.SecurityConstants;
import it.gennystabile.ricettariobe.utils.enumeration.Colore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllersConstants.REQUEST_MAPPING_INGREDIENTI)
@PreAuthorize(SecurityConstants.ALL_PROFILES)
@Validated
public class IngredienteController {

    private final IngredienteService ingredienteService;


    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }


    @GetMapping
    public ResponseEntity<List<IngredienteOutputDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredienteService.getAllIngredienti());
    }

    @GetMapping("{name}")
    public ResponseEntity<IngredienteOutputDto> getIngredientByName(@NotBlank @PathVariable String name) {
        return ResponseEntity.ok(ingredienteService.getIngredienteByNome(name));
    }

    @PostMapping
    public ResponseEntity<IngredienteOutputDto> postIngredient(@Valid @RequestBody IngredienteInputDto inputDto,
                                                               @RequestParam Colore colore) {
        return ResponseEntity.ok(ingredienteService.postIngrediente(inputDto, colore));
    }

    @PreAuthorize(SecurityConstants.ADMIN)
    @DeleteMapping
    public ResponseEntity<IngredienteOutputDto> deleteIngredient(@NotBlank @RequestParam String nome) {
        return ResponseEntity.ok(ingredienteService.deleteByNome(nome));

    }

    @PreAuthorize(SecurityConstants.ADMIN)
    @PostMapping(ControllersConstants.REQUEST_MAPPING_CATEGORIE_INGREDIENTI)
    public ResponseEntity<CategoriaIngredienteOutputDto> postCategoriaIngrediente(@Valid @RequestBody CategoriaIngredienteInputDto inputDto) {
        return ResponseEntity.ok(ingredienteService.postCategoriaIngrediente(inputDto));
    }

    @PreAuthorize(SecurityConstants.ADMIN)
    @GetMapping(ControllersConstants.REQUEST_MAPPING_CATEGORIE_INGREDIENTI)
    public ResponseEntity<List<CategoriaIngredienteOutputDto>> getAllCategorieIngrediente() {
        return ResponseEntity.ok(ingredienteService.getAllCategoriaIngrediente());
    }

}
