package it.gennystabile.ricettariobe.mapper;

import it.gennystabile.ricettariobe.dto.user.UserInputDto;
import it.gennystabile.ricettariobe.dto.user.UserOutputDto;
import it.gennystabile.ricettariobe.model.User;
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
