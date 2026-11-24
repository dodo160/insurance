package com.insurance.user.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = InsuranceMapper.class)
public interface UserMapper extends CommonMapper<User, UserDTO> {

    @Override
    @Mapping(target = "userType", expression = "java(entity.getUserType())")
    UserDTO toDto(User entity);

    @Override
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    @Mapping(target = "insurances", ignore = true)
    @Mapping(target = "temporalEntities", ignore = true)
    User fromDto(UserDTO dto);
}
