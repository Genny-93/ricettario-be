package it.gennystabile.ricettario_be.service;

import it.gennystabile.ricettario_be.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettario_be.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettario_be.exception.ResourceNotFoundException;
import it.gennystabile.ricettario_be.mapper.RicettaMapper;
import it.gennystabile.ricettario_be.model.Ricetta;
import it.gennystabile.ricettario_be.model.User;
import it.gennystabile.ricettario_be.repository.RicettaRepository;
import it.gennystabile.ricettario_be.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RicettaService {

    private final RicettaRepository ricettaRepository;
    private final UserRepository userRepository;
    private final RicettaMapper ricettaMapper;

    public RicettaService(RicettaRepository ricettaRepository, UserRepository userRepository, RicettaMapper ricettaMapper) {
        this.ricettaRepository = ricettaRepository;
        this.userRepository = userRepository;
        this.ricettaMapper = ricettaMapper;
    }


    public List<RicettaOutputDto> getAllRicette() {

        return new ArrayList<>(ricettaRepository.findAll().stream()
                .map(ricetta -> {
                    return ricettaMapper.toOutputDto(ricetta);
                })
                .toList());
    }

    public RicettaOutputDto getRicettaById(Long id) {
        Ricetta ricetta = findRicettaById(id);
        return ricettaMapper.toOutputDto(ricetta);
    }


    public RicettaOutputDto getRicettaByTitolo(String titolo) {
        return ricettaMapper.toOutputDto(findRicettaByTitle(titolo));
    }

    public RicettaOutputDto postRicetta(RicettaInputDto ricettaInputDto) throws Exception {
        Ricetta ricetta = ricettaMapper.toRicetta(ricettaInputDto);

        //TODO da modificare il return
        if (ricettaRepository.findByTitolo(ricetta.getTitolo()).isPresent()) return null;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User autore = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Utente non trovato"));
        setRicetta(ricetta, autore);
        return ricettaMapper.toOutputDto(ricettaRepository.save(ricetta));
    }

    private static void setRicetta(Ricetta ricetta, User autore) {
        ricetta.setCreatedBy(autore);
        ricetta.setUpdatedBy(autore);
        ricetta.setCreatedAt(LocalDateTime.now());
        ricetta.setUpdatedAt(LocalDateTime.now());
    }


    private Ricetta findRicettaById(Long id) {
        return ricettaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ricetta con id " + id + " non trovata!"));
    }

    private Ricetta findRicettaByTitle(String titolo) {
        return ricettaRepository.findByTitolo(titolo)
                .orElseThrow(() -> new ResourceNotFoundException("Ricetta con titolo '" + titolo + "' non trovata!"));
    }

      /* private <T> Ricetta getRicetta(T parametroDiRicerca) {
        return (parametroDiRicerca instanceof String) ?
                ricettaRepository.findByTitolo(parametroDiRicerca.toString()).orElseThrow(() -> new ResourceNotFoundException("Ricetta con titolo " + parametroDiRicerca + " non trovata!"))
                : ricettaRepository.findById((Long) parametroDiRicerca).orElseThrow(() -> new ResourceNotFoundException("Ricetta con id " + parametroDiRicerca + " non trovata!"));
    }*/
}
