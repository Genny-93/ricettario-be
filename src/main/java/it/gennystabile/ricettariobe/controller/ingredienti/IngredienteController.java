package it.gennystabile.ricettariobe.controller.ingredienti;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteOutputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.stagione.StagioneOutputDto;
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
@Tag(name = "Ingredienti", description = "Endpoint per la gestione degli ingredienti, delle loro categorie e delle stagionalità")
public class IngredienteController {

    private final IngredienteService ingredienteService;


    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @Operation(
            summary = "Recupera tutti gli ingredienti",
            description = "Restituisce l'elenco completo di tutti gli ingredienti censiti all'interno del sistema."
    )
    @GetMapping
    public ResponseEntity<List<IngredienteOutputDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredienteService.getAllIngredienti());
    }

    @Operation(
            summary = "Recupera un ingrediente tramite nome",
            description = "Effettua la ricerca di uno specifico ingrediente utilizzando il nome fornito come parametro nell'URL."
    )
    @GetMapping("{name}")
    public ResponseEntity<IngredienteOutputDto> getIngredientByName(@NotBlank @PathVariable String name) {
        return ResponseEntity.ok(ingredienteService.getIngredienteByNome(name));
    }

    @Operation(
            summary = "Recupera tutte le categorie degli ingredienti",
            description = "Restituisce la lista di tutte le categorie merceologiche associate agli ingredienti. Endpoint riservato agli amministratori."
    )
    @PreAuthorize(SecurityConstants.ADMIN)
    @GetMapping(ControllersConstants.REQUEST_MAPPING_CATEGORIE_INGREDIENTI)
    public ResponseEntity<List<CategoriaIngredienteOutputDto>> getAllCategorieIngrediente() {
        return ResponseEntity.ok(ingredienteService.getAllCategoriaIngrediente());
    }

    @Operation(
            summary = "Recupera l'elenco delle stagioni",
            description = "Restituisce la lista di tutte le stagioni dell'anno utilizzate per mappare la stagionalità degli ingredienti."
    )
    @GetMapping(ControllersConstants.REQUEST_MAPPING_STAGIONI)
    public ResponseEntity<List<StagioneOutputDto>> getAllStagioni() {
        return ResponseEntity.ok(ingredienteService.getAllStagioni());
    }

    @Operation(
            summary = "Crea un nuovo ingrediente",
            description = "Inserisce un nuovo ingrediente nel sistema sulla base dei dati forniti nel corpo della richiesta e del colore specificato come parametro."
    )
    @PostMapping
    public ResponseEntity<IngredienteOutputDto> postIngredient(@Valid @RequestBody IngredienteInputDto inputDto,
                                                               @RequestParam Colore colore) {
        return ResponseEntity.ok(ingredienteService.postIngrediente(inputDto, colore));
    }

    @Operation(
            summary = "Elimina un ingrediente tramite nome",
            description = "Rimuove definitivamente un ingrediente dal sistema identificandolo attraverso il nome fornito in query string. Endpoint riservato agli amministratori."
    )
    @PreAuthorize(SecurityConstants.ADMIN)
    @DeleteMapping
    public ResponseEntity<IngredienteOutputDto> deleteIngredient(@NotBlank @RequestParam String nome) {
        return ResponseEntity.ok(ingredienteService.deleteByNome(nome));

    }

    @Operation(
            summary = "Crea una nuova categoria per gli ingredienti",
            description = "Inserisce una nuova categoria all'interno del sistema. Endpoint riservato agli amministratori."
    )
    @PreAuthorize(SecurityConstants.ADMIN)
    @PostMapping(ControllersConstants.REQUEST_MAPPING_CATEGORIE_INGREDIENTI)
    public ResponseEntity<CategoriaIngredienteOutputDto> postCategoriaIngrediente(@Valid @RequestBody CategoriaIngredienteInputDto inputDto) {
        return ResponseEntity.ok(ingredienteService.postCategoriaIngrediente(inputDto));
    }


}
