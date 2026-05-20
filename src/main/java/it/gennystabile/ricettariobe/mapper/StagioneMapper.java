package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ingrediente.stagione.StagioneOutputDto;
import it.gennystabile.ricettariobe.model.Stagione;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StagioneMapper {


    StagioneOutputDto toOutputDto(Stagione stagione);
}
