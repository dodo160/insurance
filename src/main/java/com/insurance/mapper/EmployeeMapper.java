package com.insurance.mapper;

import com.insurance.model.Employee;
import com.insurance.modeldto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface EmployeeMapper {

    Employee fromDto(UserDTO dto);

    UserDTO toDto(Employee entity);
}
