package it.gennystabile.ricettariobe.service;

import it.gennystabile.ricettariobe.dto.ricetta.RicettaCardOutputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettariobe.exception.DuplicateException;
import it.gennystabile.ricettariobe.exception.ResourceNotFoundException;
import it.gennystabile.ricettariobe.mapper.RicettaMapper;
import it.gennystabile.ricettariobe.model.*;
import it.gennystabile.ricettariobe.repository.*;
import it.gennystabile.ricettariobe.utils.CollectionUtils;
import it.gennystabile.ricettariobe.utils.enumeration.TipoFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RicettaService {

    private final RicettaRepository ricettaRepository;
    private final UserRepository userRepository;
    private final RicettaMapper ricettaMapper;
    private final MultimediaRepository multimediaRepository;
    private final CategoriaRicettaRepository categoriaRicettaRepository;
    private final IngredienteService ingredienteService;
    private final ComposizioneRicettaRepository composizioneRicettaRepository;

    public RicettaService(RicettaRepository ricettaRepository, UserRepository userRepository, RicettaMapper ricettaMapper, MultimediaRepository multimediaRepository, CategoriaRicettaRepository categoriaRicettaRepository, IngredienteService ingredienteService, ComposizioneRicettaRepository composizioneRicettaRepository) {
        this.ricettaRepository = ricettaRepository;
        this.userRepository = userRepository;
        this.ricettaMapper = ricettaMapper;
        this.multimediaRepository = multimediaRepository;
        this.categoriaRicettaRepository = categoriaRicettaRepository;
        this.ingredienteService = ingredienteService;
        this.composizioneRicettaRepository = composizioneRicettaRepository;
    }


    public List<RicettaOutputDto> getAllRicette() {

        return new ArrayList<>(ricettaRepository.findAll().stream()
                .map(ricetta -> ricettaMapper.toOutputDto(ricetta))
                .toList());
    }

    public List<RicettaCardOutputDto> getAllRicetteCards() {
        return ricettaRepository.findAllRecipesForCards().stream()
                .map(ricetta -> {
                    return ricettaMapper.toOutputCardDto(ricetta);
                })
                .toList();
    }

    public RicettaOutputDto getRicettaById(Long id) {
        Ricetta ricetta = findRicettaById(id);
        return ricettaMapper.toOutputDto(ricetta);
    }


    public RicettaOutputDto getRicettaByTitolo(String titolo) {
        return ricettaMapper.toOutputDto(findRicettaByTitle(titolo));
    }

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
            Optional<CategoriaRicetta> categoriaRicetta = categoriaRicettaRepository.findByNomeCategoriaIgnoreCase(categoria);
            if (categoriaRicetta.isPresent()) listaCategorieRicetta.add(categoriaRicetta.get());
        });
        if (CollectionUtils.isNotEmpty(listaCategorieRicetta)) {
            ricetta.setCategorie(listaCategorieRicetta);
        }

        //setto i multimedia collegati alla ricetta, se la lista non è vuota
        if (CollectionUtils.isNotEmpty(ricetta.getMultimedia())) {
            setMultimedia(ricetta.getMultimedia(), ricetta);
        }


        //setto la composizione della ricetta
        List<ComposizioneRicetta> composizioneRicetta = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ricettaInputDto.getComposizioneRicetta())) {
            Ricetta finalRicetta = ricetta;
            composizioneRicetta = ricettaInputDto.getComposizioneRicetta().stream().map(elemento -> {
                Ingrediente ingrediente = ingredienteService.findIngredienteByNome(elemento.getIngrediente());
                ComposizioneRicetta ingredienteSingolo = new ComposizioneRicetta();
                ingredienteSingolo.setIngrediente(ingrediente);
                ingredienteSingolo.setQuantita(elemento.getQuantita());
                ingredienteSingolo.setUnitaDiMisura(elemento.getUnitaDiMisura());
                ingredienteSingolo.setRicetta(finalRicetta);
                return ingredienteSingolo;
            }).toList();
        }

        ricetta.setComposizioneRicetta(composizioneRicetta);

        ricetta = ricettaRepository.save(ricetta);
        return ricettaMapper.toOutputDto(ricetta);
    }

    public List<RicettaCardOutputDto> findRecipesByCategory(String category) {
        List<Ricetta.RicettaCardProjection> listaRicette = ricettaRepository.findByCategorie_NomeCategoriaIgnoreCase(category);
        if (CollectionUtils.isEmpty(listaRicette)) throw new ResourceNotFoundException("Ricette non trovate");

        return listaRicette.stream()
                .map(ricetta -> {
                    return ricettaMapper.toOutputCardDto(ricetta);
                })
                .toList();
    }

    public List<RicettaCardOutputDto> getRicettaByUserId(Long userId) {

        return ricettaRepository.findByCreatedBy_Id(userId).stream()
                .map(ricetta -> {
                    return ricettaMapper.toOutputCardDto(ricetta);
                }).toList();
    }

    public RicettaOutputDto deleteByNome(String titolo) {
        Ricetta ricetta = ricettaRepository.findByTitolo(titolo).orElseThrow(() -> new ResourceNotFoundException("Risorsa non trovata"));
        ricettaRepository.deleteById(ricetta.getId());
        return ricettaMapper.toOutputDto(ricetta);
    }

    public Float aggiornaValutazione(Long id, Float voto) {
        Ricetta ricetta = ricettaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ricetta non trovata"));

        int votiTotali = ricetta.getVotiTotali() + 1;
        ricetta.setVotiTotali(votiTotali);
        Float oldValutazioneMedia = ricetta.getValutazioneMedia();
        Float newValutazioneMedia = oldValutazioneMedia + ((voto - oldValutazioneMedia) / votiTotali);
        ricetta.setValutazioneMedia(newValutazioneMedia);
        ricettaRepository.save(ricetta);
        return newValutazioneMedia;

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
        ricetta.setVotiTotali(0);
        ricetta.setValutazioneMedia(0F);
    }

    private void setMultimedia(List<Multimedia> listaMultimedia, Ricetta ricetta) {

        listaMultimedia.forEach(multimedia -> {
            multimedia.setRicetta(ricetta);

            String url = multimedia.getUrl();
            int index = url.lastIndexOf('.');
            multimedia.setFormato(url.substring(index + 1));
            multimedia.setTipoFile(TipoFile.fromExtensions(multimedia.getFormato()));
        });
    }


      /* private <T> Ricetta getRicetta(T parametroDiRicerca) {
        return (parametroDiRicerca instanceof String) ?
                ricettaRepository.findByTitolo(parametroDiRicerca.toString()).orElseThrow(() -> new ResourceNotFoundException("Ricetta con titolo " + parametroDiRicerca + " non trovata!"))
                : ricettaRepository.findById((Long) parametroDiRicerca).orElseThrow(() -> new ResourceNotFoundException("Ricetta con id " + parametroDiRicerca + " non trovata!"));
    }*/
}
