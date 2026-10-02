package com.insurance.user.mapper;

import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.model.Employee;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface EmployeeMapper {

    Employee fromDto(UserDTO dto);

    UserDTO toDto(Employee entity);
}
