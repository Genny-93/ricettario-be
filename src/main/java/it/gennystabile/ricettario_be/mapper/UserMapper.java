package it.gennystabile.ricettario_be.mapper;

import it.gennystabile.ricettario_be.dto.user.UserInputDto;
import it.gennystabile.ricettario_be.dto.user.UserOutputDto;
import it.gennystabile.ricettario_be.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserOutputDto toOutputDto(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUtente(UserInputDto inputDto);
}
