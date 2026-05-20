package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettariobe.model.Ricetta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoriaRicettaMapper.class})
public interface RicettaMapper {

    RicettaOutputDto toOutputDto(Ricetta ricetta);

    @Mapping(target = "multimedia", source = "listaMultimedia")
    Ricetta toRicetta(RicettaInputDto inputDto);
}
