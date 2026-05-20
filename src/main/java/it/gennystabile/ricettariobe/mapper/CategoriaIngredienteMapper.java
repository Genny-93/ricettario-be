package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteInputDto;
import it.gennystabile.ricettariobe.dto.ingrediente.categoria.CategoriaIngredienteOutputDto;
import it.gennystabile.ricettariobe.model.CategoriaIngrediente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaIngredienteMapper {

    CategoriaIngrediente toModelDto(CategoriaIngredienteInputDto inputDto);

    CategoriaIngredienteOutputDto toOutputDto(CategoriaIngrediente categoriaIngrediente);
}
