package com.insurance.mapper;

import com.insurance.model.User;
import com.insurance.modeldto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = InsuranceMapper.class)
public interface UserMapper {

    @Mappings(value = {
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "firstName", source = "entity.firstName"),
            @Mapping(target = "lastName", source = "entity.lastName"),
            @Mapping(target = "city", source = "entity.city"),
            @Mapping(target = "address", source = "entity.address"),
            @Mapping(target = "postCode", source = "entity.postCode"),
            @Mapping(target = "identityId", source = "entity.identityId"),
            @Mapping(target = "userType", expression = "java(entity.getUserType())")
    })
    UserDTO userToUserDTO(User entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "firstName", source = "dto.firstName"),
            @Mapping(target = "lastName", source = "dto.lastName"),
            @Mapping(target = "city", source = "dto.city"),
            @Mapping(target = "address", source = "dto.address"),
            @Mapping(target = "postCode", source = "dto.postCode"),
            @Mapping(target = "identityId", source = "dto.identityId")
    })
    User userDTOtoUser(UserDTO dto);
}
