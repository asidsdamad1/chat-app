package org.asi.authservice.mapper;

import org.asi.authservice.model.User;
import org.asi.dtomodels.UserDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", expression = "java(convertIdToString(user.getId()))")
    UserDTO toDTO(User user);

    @InheritConfiguration(name = "toDTO")
    @Mapping(target = "activationKey", ignore = true)
    UserDTO mapToUserDTOWithoutActivationKey(User user);

    default String convertIdToString(UUID id) {
        if (id == null) {
            return null;
        }
        return id.toString();
    }


}
