package com.insurance.mapper;

import com.insurance.model.Client;
import com.insurance.modeldto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface ClientMapper {

    Client fromDto(UserDTO dto);

    UserDTO toDto(Client client);
}
