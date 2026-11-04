package com.insurance.user.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.model.Client;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface ClientMapper extends CommonMapper<Client, UserDTO> {
}
