package com.insurance.user.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = InsuranceMapper.class)
public interface EmployeeMapper extends CommonMapper<Employee, UserDTO> {

    @Override
    @Mapping(target = "insurances", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    @Mapping(target = "temporalEntities", ignore = true)
    Employee fromDto(UserDTO dto);
}
