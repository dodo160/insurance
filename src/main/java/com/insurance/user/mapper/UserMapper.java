package com.insurance.user.mapper;

import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.model.User;
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
