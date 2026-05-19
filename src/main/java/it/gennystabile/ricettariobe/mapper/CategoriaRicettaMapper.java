package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;
import it.gennystabile.ricettariobe.model.CategoriaRicetta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaRicettaMapper {

    @Mapping(target = "nomeCategoriaRicetta", source = "nomeCategoria")
    CategoriaOutputDto toCategoriaOutputDto(CategoriaRicetta inputDto);
}
