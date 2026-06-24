package it.gennystabile.ricettariobe.controller.categorie;

import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;
import it.gennystabile.ricettariobe.service.CategoriaRicettaService;
import it.gennystabile.ricettariobe.utils.constant.SecurityConstants;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.gennystabile.ricettariobe.utils.constant.ControllersConstants.REQUEST_MAPPING_CATEGORIE_RICETTE;

@RestController
@RequestMapping(REQUEST_MAPPING_CATEGORIE_RICETTE)
@PreAuthorize(SecurityConstants.ALL_PROFILES)
@Validated
public class CategorieController {
    private final CategoriaRicettaService categoriaRicettaService;

    public CategorieController(CategoriaRicettaService categoriaRicettaService) {
        this.categoriaRicettaService = categoriaRicettaService;
    }

    @PostMapping
    @PreAuthorize(SecurityConstants.ADMIN)
    public ResponseEntity<CategoriaOutputDto> postCategoriaRicetta(@NotBlank @RequestParam String nomeCategoria) {
        return ResponseEntity.ok(categoriaRicettaService.postCategoria(nomeCategoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaOutputDto>> getAllCategories() {
        return ResponseEntity.ok(categoriaRicettaService.getAllCategories());
    }
}
