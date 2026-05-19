package it.gennystabile.ricettariobe.service;


import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.exception.DuplicateException;
import it.gennystabile.ricettariobe.exception.ResourceNotFoundException;
import it.gennystabile.ricettariobe.mapper.IngredienteMapper;
import it.gennystabile.ricettariobe.model.Ingrediente;
import it.gennystabile.ricettariobe.model.User;
import it.gennystabile.ricettariobe.repository.IngredienteRepository;
import it.gennystabile.ricettariobe.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final IngredienteMapper ingredienteMapper;
    private final UserRepository userRepository;


    public IngredienteService(IngredienteRepository ingredienteRepository, IngredienteMapper ingredienteMapper, UserRepository userRepository) {
        this.ingredienteRepository = ingredienteRepository;
        this.ingredienteMapper = ingredienteMapper;
        this.userRepository = userRepository;
    }

    public List<IngredienteOutputDto> getAllIngredients() {
        List<IngredienteOutputDto> listaIngredienti = new ArrayList<>();

        return listaIngredienti = ingredienteRepository.findAll()
                .stream()
                .map(ingrediente -> ingredienteMapper.toOutputDto(ingrediente))
                .toList();
    }

    public IngredienteOutputDto getIngredientByName(String name) {

        Ingrediente ingrediente = ingredienteRepository.findByNome(name).orElseThrow(() -> new ResourceNotFoundException("Ingrediente non trovato"));

        return ingredienteMapper.toOutputDto(ingrediente);
    }

    public IngredienteOutputDto postIngredient(IngredienteInputDto inputDto) {
        if (ingredienteRepository.existsByNomeIgnoreCase(inputDto.getNome()))
            throw new DuplicateException("Ingrediente già esistente");

        Ingrediente ingrediente = ingredienteMapper.toModel(inputDto);
        ingrediente.setCreatedAt(LocalDateTime.now());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            ingrediente.setCreatedBy(user.get());
        }

        return ingredienteMapper.toOutputDto(ingredienteRepository.save(ingrediente));
    }
}
