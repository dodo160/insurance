package com.insurance.mapper;

import com.insurance.model.Employee;
import com.insurance.modeldto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = InsuranceMapper.class)
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "firstName", source = "dto.firstName"),
            @Mapping(target = "lastName", source = "dto.lastName"),
            @Mapping(target = "city", source = "dto.city"),
            @Mapping(target = "address", source = "dto.address"),
            @Mapping(target = "postCode", source = "dto.postCode"),
            @Mapping(target = "identityId", source = "dto.identityId")
    })
    Employee userDTOtoEmployee(UserDTO dto);
}
