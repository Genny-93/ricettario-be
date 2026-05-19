package it.gennystabile.ricettariobe.controller.ricette;


import it.gennystabile.ricettariobe.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;
import it.gennystabile.ricettariobe.service.RicettaService;
import it.gennystabile.ricettariobe.utils.constant.controller.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.gennystabile.ricettariobe.utils.constant.controller.ControllersConstants.REQUEST_MAPPING_CATEGORIE_RICETTE;
import static it.gennystabile.ricettariobe.utils.constant.controller.ControllersConstants.REQUEST_MAPPING_RICETTE;

@RestController
@RequestMapping(REQUEST_MAPPING_RICETTE)
@PreAuthorize(SecurityConstants.ALL_PROFILES)
@Validated
public class RicettaController {

    private final RicettaService ricettaService;

    public RicettaController(RicettaService ricettaService) {
        this.ricettaService = ricettaService;
    }


    @GetMapping()
    public ResponseEntity<List<RicettaOutputDto>> getAllRicette() {
        return ResponseEntity.ok(ricettaService.getAllRicette());
    }

    @GetMapping("{id}")
    public ResponseEntity<RicettaOutputDto> getRicettaById(@PathVariable Long id) {
        RicettaOutputDto ricetta = ricettaService.getRicettaById(id);
        return (ricetta == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(ricetta);
    }

    @GetMapping("{title}")
    public ResponseEntity<RicettaOutputDto> getRicettaByTitolo(@NotBlank @PathVariable String title) {
        RicettaOutputDto ricetta = ricettaService.getRicettaByTitolo(title);
        return (ricetta == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(ricetta);
    }

    @PostMapping()
    public ResponseEntity<RicettaOutputDto> postRicetta(
            @Valid @RequestBody RicettaInputDto ricettaInputDto) throws Exception {
        return ResponseEntity.ok(ricettaService.postRicetta(ricettaInputDto));
    }

    @PostMapping(REQUEST_MAPPING_CATEGORIE_RICETTE)
    @PreAuthorize(SecurityConstants.ADMIN)
    public ResponseEntity<CategoriaOutputDto> postCategoriaRicetta(@RequestParam String nomeCategoria) {
        return ResponseEntity.ok(ricettaService.postCategoria(nomeCategoria));
    }
}
