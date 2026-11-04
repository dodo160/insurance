package com.insurance.user.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.model.Employee;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface EmployeeMapper extends CommonMapper<Employee, UserDTO> {
}
