package it.gennystabile.ricettario_be.controller.ricette;


import it.gennystabile.ricettario_be.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettario_be.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettario_be.service.RicettaService;
import it.gennystabile.ricettario_be.utils.constant.controller.SecurityConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.gennystabile.ricettario_be.utils.constant.controller.UsersConstants.REQUEST_MAPPING_RICETTE;

@RestController
@RequestMapping(REQUEST_MAPPING_RICETTE)
@PreAuthorize(SecurityConstants.ALL_PROFILES)
public class RicetteController {

    private final RicettaService ricettaService;

    public RicetteController(RicettaService ricettaService) {
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

    @GetMapping("{titolo}")
    public ResponseEntity<RicettaOutputDto> getRicettaByTitolo(@PathVariable String titolo) {
        RicettaOutputDto ricetta = ricettaService.getRicettaByTitolo(titolo);
        return (ricetta == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(ricetta);
    }

    @PostMapping()
    public ResponseEntity<RicettaOutputDto> postRicetta(
            @RequestBody RicettaInputDto ricettaInputDto) throws Exception {
        return ResponseEntity.ok(ricettaService.postRicetta(ricettaInputDto));
    }
}
