package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.model.Ingrediente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {StagioneMapper.class})
public interface IngredienteMapper {


    IngredienteOutputDto toOutputDto(Ingrediente inputDto);

    @Mapping(target = "stagioni", ignore = true)
    @Mapping(target = "categoriaIngrediente",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "colorePrincipale",ignore = true)
    @Mapping(target = "createdBy",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    Ingrediente toModel(IngredienteInputDto inputDto);
}
