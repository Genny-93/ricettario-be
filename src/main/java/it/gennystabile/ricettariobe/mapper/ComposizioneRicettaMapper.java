package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ricetta.composizionericetta.ComposizioneRicettaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.composizionericetta.ComposizioneRicettaOutputDto;
import it.gennystabile.ricettariobe.model.ComposizioneRicetta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComposizioneRicettaMapper {

    ComposizioneRicettaOutputDto toOutputDto (ComposizioneRicetta composizioneRicetta);

    @Mapping(target = "ingrediente", ignore = true)
    ComposizioneRicetta toModelDto (ComposizioneRicettaInputDto inputDto);

}
