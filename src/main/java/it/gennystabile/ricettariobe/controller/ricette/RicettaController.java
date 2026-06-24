package it.gennystabile.ricettariobe.controller.ricette;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaCardOutputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettariobe.service.RicettaService;
import it.gennystabile.ricettariobe.utils.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.gennystabile.ricettariobe.utils.constant.ControllersConstants.REQUEST_MAPPING_RICETTE;

@RestController
@RequestMapping(REQUEST_MAPPING_RICETTE)
@PreAuthorize(SecurityConstants.ALL_PROFILES)
@Validated
@Tag(name = "Ricette", description = "Endpoint per la consultazione, la creazione e la rimozione delle ricette e delle loro categorie")
public class RicettaController {

    private final RicettaService ricettaService;

    public RicettaController(RicettaService ricettaService) {
        this.ricettaService = ricettaService;
    }

    @Operation(
            summary = "Recupera tutte le ricette",
            description = "Restituisce l'elenco completo di tutte le ricette disponibili nel sistema."
    )
    @GetMapping()
    public ResponseEntity<List<RicettaOutputDto>> getAllRicette() {
        return ResponseEntity.ok(ricettaService.getAllRicette());
    }

    @GetMapping("/cards")
    public ResponseEntity<List<RicettaCardOutputDto>> getAllRicetteCards() {
        return ResponseEntity.ok(ricettaService.getAllRicetteCards());
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<RicettaCardOutputDto>> findRecipesByCategory(@NotBlank @PathVariable String categoryName) {
        return ResponseEntity.ok(ricettaService.findRecipesByCategory(categoryName));
    }

    @Operation(
            summary = "Recupera una ricetta tramite ID",
            description = "Effettua la ricerca di una specifica ricetta utilizzando l'identificativo nu merico fornito nell'URL. Restituisce uno stato 204 (No Content) se la risorsa non viene trovata."
    )
    @GetMapping("{id}")
    public ResponseEntity<RicettaOutputDto> getRicettaById(@PathVariable Long id) {
        RicettaOutputDto ricetta = ricettaService.getRicettaById(id);
        return (ricetta == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(ricetta);
    }

    @Operation(
            summary = "Recupera una ricetta tramite titolo",
            description = "Effettua la ricerca di una singola ricetta basandosi sul titolo testuale inserito nell'URL. Restituisce uno stato 204 (No Content) in assenza di corrispondenze."
    )
    @GetMapping("/title/{title}")
    public ResponseEntity<RicettaOutputDto> getRicettaByTitolo(@NotBlank @PathVariable String title) {
        RicettaOutputDto ricetta = ricettaService.getRicettaByTitolo(title);
        return (ricetta == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(ricetta);
    }

    @Operation(
            summary = "Crea una nuova ricetta",
            description = "Inserisce una nuova ricetta nel sistema prendendo i dati strutturati dal corpo della richiesta."
    )
    @PostMapping()
    public ResponseEntity<RicettaOutputDto> postRicetta(
            @Valid @RequestBody RicettaInputDto ricettaInputDto) {
        return ResponseEntity.ok(ricettaService.postRicetta(ricettaInputDto));
    }

    @Operation(
            summary = "Crea una nuova categoria per le ricette",
            description = "Aggiunge una categoria di classificazione per le ricette tramite il nome passato come parametro della richiesta. Endpoint riservato agli amministratori."
    )


    @PutMapping("/{id}/rating")
    public ResponseEntity<Float> aggiornaValutazione(@PathVariable Long id, @NotNull @RequestParam Float voto) {
        return ResponseEntity.ok(ricettaService.aggiornaValutazione(id, voto));
    }

    @Operation(
            summary = "Elimina una ricetta tramite titolo",
            description = "Rimuove in modo definitivo una ricetta dal database identificandola mediante il titolo fornito nei parametri di query. Endpoint riservato agli amministratori."
    )
    @DeleteMapping
    @PreAuthorize(SecurityConstants.ADMIN)
    public ResponseEntity<RicettaOutputDto> deleteRicettaByTitolo(@NotBlank @RequestParam String titolo) {
        return ResponseEntity.ok(ricettaService.deleteByNome(titolo));
    }

}
