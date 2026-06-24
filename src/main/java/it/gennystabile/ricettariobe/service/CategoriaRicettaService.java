package it.gennystabile.ricettariobe.service;

import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;
import it.gennystabile.ricettariobe.exception.DuplicateException;
import it.gennystabile.ricettariobe.mapper.CategoriaRicettaMapper;
import it.gennystabile.ricettariobe.model.CategoriaRicetta;
import it.gennystabile.ricettariobe.repository.CategoriaRicettaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaRicettaService {

    private final CategoriaRicettaRepository categoriaRicettaRepository;
    private final CategoriaRicettaMapper categoriaRicettaMapper;

    public CategoriaRicettaService(CategoriaRicettaRepository categoriaRicettaRepository, CategoriaRicettaMapper categoriaRicettaMapper) {
        this.categoriaRicettaRepository = categoriaRicettaRepository;
        this.categoriaRicettaMapper = categoriaRicettaMapper;
    }

    public List<CategoriaOutputDto> getAllCategories() {
        return categoriaRicettaRepository.findAll().stream()
                .map(categoria -> {
                    return categoriaRicettaMapper.toCategoriaOutputDto(categoria);
                }).toList();
    }

    public CategoriaOutputDto postCategoria(String nomeCategoria) {
        if (categoriaRicettaRepository.existsByNomeCategoriaIgnoreCase(nomeCategoria))
            throw new DuplicateException("Il nome categoria inserito esiste già");
        CategoriaRicetta categoriaRicetta = new CategoriaRicetta();
        categoriaRicetta.setNomeCategoria(nomeCategoria);

        return categoriaRicettaMapper.toCategoriaOutputDto(categoriaRicettaRepository.save(categoriaRicetta));
    }
}
