package com.insurance.mapper;

import com.insurance.model.User;
import com.insurance.modeldto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = InsuranceMapper.class)
public interface UserMapper {

    @Mappings(value = {
            @Mapping(target = "userType", expression = "java(entity.getUserType())")
    })
    UserDTO toDto(User entity);

    User fromDto(UserDTO dto);
}
