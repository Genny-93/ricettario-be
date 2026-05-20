package it.gennystabile.ricettariobe.service;


import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteOutputDto;
import it.gennystabile.ricettariobe.exception.BadRequestException;
import it.gennystabile.ricettariobe.exception.DuplicateException;
import it.gennystabile.ricettariobe.exception.ResourceNotFoundException;
import it.gennystabile.ricettariobe.mapper.CategoriaIngredienteMapper;
import it.gennystabile.ricettariobe.mapper.IngredienteMapper;
import it.gennystabile.ricettariobe.model.CategoriaIngrediente;
import it.gennystabile.ricettariobe.model.Ingrediente;
import it.gennystabile.ricettariobe.model.Stagione;
import it.gennystabile.ricettariobe.model.User;
import it.gennystabile.ricettariobe.repository.CategoriaIngredienteRepository;
import it.gennystabile.ricettariobe.repository.IngredienteRepository;
import it.gennystabile.ricettariobe.repository.UserRepository;
import it.gennystabile.ricettariobe.utils.constant.ServiceConstants;
import it.gennystabile.ricettariobe.utils.enumeration.Colore;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final IngredienteMapper ingredienteMapper;
    private final UserRepository userRepository;
    private final CategoriaIngredienteRepository categoriaIngredienteRepository;
    private final CategoriaIngredienteMapper categoriaIngredienteMapper;


    public IngredienteService(IngredienteRepository ingredienteRepository, IngredienteMapper ingredienteMapper, UserRepository userRepository, CategoriaIngredienteRepository categoriaIngredienteRepository, CategoriaIngredienteMapper categoriaIngredienteMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.ingredienteMapper = ingredienteMapper;
        this.userRepository = userRepository;
        this.categoriaIngredienteRepository = categoriaIngredienteRepository;
        this.categoriaIngredienteMapper = categoriaIngredienteMapper;
    }

    public List<IngredienteOutputDto> getAllIngredienti() {
        List<IngredienteOutputDto> listaIngredienti = new ArrayList<>();

        return listaIngredienti = ingredienteRepository.findAll()
                .stream()
                .map(ingrediente -> ingredienteMapper.toOutputDto(ingrediente))
                .toList();
    }

    public IngredienteOutputDto getIngredienteByNome(String name) {

        Ingrediente ingrediente = ingredienteRepository.findByNome(name).orElseThrow(() -> new ResourceNotFoundException("Ingrediente non trovato"));

        return ingredienteMapper.toOutputDto(ingrediente);
    }

    @Transactional
    public IngredienteOutputDto postIngrediente(IngredienteInputDto inputDto, Colore colore) {


        CategoriaIngrediente categoriaIngrediente = categoriaIngredienteRepository.findByNomeCategoria(inputDto.getNomeCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria ingrediente non trovata"));

        if (ingredienteRepository.existsByNomeIgnoreCase(inputDto.getNome()))
            throw new DuplicateException("Ingrediente già esistente");


        //Set ingrediente
        Ingrediente ingrediente = ingredienteMapper.toModel(inputDto);
        ingrediente.setColorePrincipale(colore);
        ingrediente.setCategoriaIngrediente(categoriaIngrediente);
        ingrediente.setCreatedAt(LocalDateTime.now());


        //Set Stagione
        Set<Stagione> stagioni = inputDto.getStagioni().stream()
                .map(nome -> {
                    Long id = ServiceConstants.STAGIONI.get(nome);
                    if (id == null) {
                        throw new BadRequestException("Stagione non corretta: " + nome);
                    }
                    Stagione s = new Stagione();
                    s.setId(id);
                    s.setNome(nome);
                    return s;
                })
                .collect(Collectors.toSet());

        ingrediente.setStagioni(stagioni);


        //Set utente
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            ingrediente.setCreatedBy(user.get());
        }

        return ingredienteMapper.toOutputDto(ingredienteRepository.save(ingrediente));
    }

    public IngredienteOutputDto deleteByNome(String nome) {
        return ingredienteMapper.toOutputDto(ingredienteRepository.deleteByNome(nome).orElseThrow(() -> new ResourceNotFoundException("Ingrediente non trovato")));
    }

    public CategoriaIngredienteOutputDto postCategoriaIngrediente(CategoriaIngredienteInputDto inputDto) {

        if (categoriaIngredienteRepository.existsByNomeCategoriaIgnoreCase(inputDto.getNomeCategoria())) {
            throw new DuplicateException("Categoria già esistente");
        }
        CategoriaIngrediente categoriaIngrediente = categoriaIngredienteMapper.toModelDto(inputDto);
        return categoriaIngredienteMapper.toOutputDto(categoriaIngredienteRepository.save(categoriaIngrediente));
    }

    public List<CategoriaIngredienteOutputDto> getAllCategoriaIngrediente() {
        List<CategoriaIngrediente> listaCategorieIngredienti = categoriaIngredienteRepository.findAll();
        return listaCategorieIngredienti.stream()
                .map(categoriaIngrediente -> {
                    return categoriaIngredienteMapper.toOutputDto(categoriaIngrediente);
                })
                .toList();
    }
}
