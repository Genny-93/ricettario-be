package it.gennystabile.ricettario_be.mapper;

import it.gennystabile.ricettario_be.dto.ricetta.RicettaInputDto;
import it.gennystabile.ricettario_be.dto.ricetta.RicettaOutputDto;
import it.gennystabile.ricettario_be.model.Ricetta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RicettaMapper {

    RicettaOutputDto toOutputDto(Ricetta ricetta);

    Ricetta toRicetta(RicettaInputDto inputDto);
}
