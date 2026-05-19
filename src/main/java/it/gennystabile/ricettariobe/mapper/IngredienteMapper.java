package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;
import it.gennystabile.ricettariobe.model.Ingrediente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredienteMapper {

    IngredienteOutputDto toOutputDto(Ingrediente inputDto);

    Ingrediente toModel(IngredienteInputDto inputDto);
}
