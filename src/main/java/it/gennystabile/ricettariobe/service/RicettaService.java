package it.gennystabile.ricettariobe.service;

import it.gennystabile.ricettariobe.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;
import it.gennystabile.ricettariobe.exception.DuplicateException;
import it.gennystabile.ricettariobe.exception.ResourceNotFoundException;
import it.gennystabile.ricettariobe.mapper.CategoriaRicettaMapper;
import it.gennystabile.ricettariobe.mapper.RicettaMapper;
import it.gennystabile.ricettariobe.model.CategoriaRicetta;
import it.gennystabile.ricettariobe.model.Multimedia;
import it.gennystabile.ricettariobe.model.Ricetta;
import it.gennystabile.ricettariobe.model.User;
import it.gennystabile.ricettariobe.repository.CategoriaRicettaRepository;
import it.gennystabile.ricettariobe.repository.MultimediaRepository;
import it.gennystabile.ricettariobe.repository.RicettaRepository;
import it.gennystabile.ricettariobe.repository.UserRepository;
import it.gennystabile.ricettariobe.utils.CollectionUtils;
import it.gennystabile.ricettariobe.utils.enumeration.TipoFile;
import jakarta.transaction.Transactional;
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
    private final MultimediaRepository multimediaRepository;
    private final CategoriaRicettaRepository categoriaRicettaRepository;
    private final CategoriaRicettaMapper categoriaRicettaMapper;

    public RicettaService(RicettaRepository ricettaRepository, UserRepository userRepository, RicettaMapper ricettaMapper, MultimediaRepository multimediaRepository, CategoriaRicettaRepository categoriaRicettaRepository, CategoriaRicettaMapper categoriaRicettaMapper) {
        this.ricettaRepository = ricettaRepository;
        this.userRepository = userRepository;
        this.ricettaMapper = ricettaMapper;
        this.multimediaRepository = multimediaRepository;
        this.categoriaRicettaRepository = categoriaRicettaRepository;
        this.categoriaRicettaMapper = categoriaRicettaMapper;
    }


    public List<RicettaOutputDto> getAllRicette() {

        return new ArrayList<>(ricettaRepository.findAll().stream()
                .map(ricetta -> ricettaMapper.toOutputDto(ricetta))
                .toList());
    }

    public RicettaOutputDto getRicettaById(Long id) {
        Ricetta ricetta = findRicettaById(id);
        return ricettaMapper.toOutputDto(ricetta);
    }


    public RicettaOutputDto getRicettaByTitolo(String titolo) {
        return ricettaMapper.toOutputDto(findRicettaByTitle(titolo));
    }


    @Transactional
    public RicettaOutputDto postRicetta(RicettaInputDto ricettaInputDto) {
        Ricetta ricetta = ricettaMapper.toRicetta(ricettaInputDto);

        if (ricettaRepository.findByTitolo(ricetta.getTitolo()).isPresent())
            throw new DuplicateException("Esiste già una ricetta con questo nome");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User autore = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        //setto i campi della ricetta
        setRicetta(ricetta, autore);

        //setto la CategoriaRicetta
        List<CategoriaRicetta> listaCategorieRicetta = new ArrayList<>();
        ricettaInputDto.getCategorieRicetta().forEach(categoria -> {
            Optional<CategoriaRicetta> categoriaRicetta = categoriaRicettaRepository.findByNomeCategoria(categoria);
            if (categoriaRicetta.isPresent()) listaCategorieRicetta.add(categoriaRicetta.get());
        });
        if (CollectionUtils.isNotEmpty(listaCategorieRicetta)) {
            ricetta.setCategorie(listaCategorieRicetta);
        }

        //Salvo l'entity
        ricetta = ricettaRepository.save(ricetta);


        //setto i multimedia collegati alla ricetta, se la lista non è vuota
        if (CollectionUtils.isNotEmpty(ricetta.getMultimedia())) {
            setMultimedia(ricetta.getMultimedia(), ricetta);
        }


        //SETTARE CATEGORIA RICETTA
        return ricettaMapper.toOutputDto(ricetta);
    }

    public CategoriaOutputDto postCategoria(String nomeCategoria) {
        if (categoriaRicettaRepository.existsByNomeCategoriaIgnoreCase(nomeCategoria))
            throw new DuplicateException("Il nome categoria inserito esiste già");
        CategoriaRicetta categoriaRicetta = new CategoriaRicetta();
        categoriaRicetta.setNomeCategoria(nomeCategoria);

        return categoriaRicettaMapper.toCategoriaOutputDto(categoriaRicettaRepository.save(categoriaRicetta));
    }

    //Metodi di Utility

    private Ricetta findRicettaById(Long id) {
        return ricettaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ricetta con id " + id + " non trovata!"));
    }

    private Ricetta findRicettaByTitle(String titolo) {
        return ricettaRepository.findByTitolo(titolo)
                .orElseThrow(() -> new ResourceNotFoundException("Ricetta con titolo '" + titolo + "' non trovata!"));
    }

    private void setRicetta(Ricetta ricetta, User autore) {
        ricetta.setCreatedBy(autore);
        ricetta.setUpdatedBy(autore);
        ricetta.setCreatedAt(LocalDateTime.now());
        ricetta.setUpdatedAt(LocalDateTime.now());
    }

    private void setMultimedia(List<Multimedia> listaMultimedia, Ricetta ricetta) {

        listaMultimedia.forEach(multimedia -> {
            multimedia.setRicetta(ricetta);

            String url = multimedia.getUrl();
            int index = url.lastIndexOf('.');
            multimedia.setFormato(url.substring(index + 1));
            multimedia.setTipoFile(TipoFile.fromExtensions(multimedia.getFormato()));
        });
        multimediaRepository.saveAll(listaMultimedia);
    }


      /* private <T> Ricetta getRicetta(T parametroDiRicerca) {
        return (parametroDiRicerca instanceof String) ?
                ricettaRepository.findByTitolo(parametroDiRicerca.toString()).orElseThrow(() -> new ResourceNotFoundException("Ricetta con titolo " + parametroDiRicerca + " non trovata!"))
                : ricettaRepository.findById((Long) parametroDiRicerca).orElseThrow(() -> new ResourceNotFoundException("Ricetta con id " + parametroDiRicerca + " non trovata!"));
    }*/
}
