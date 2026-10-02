package com.insurance.insurance.mapper;

import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.model.Insurance;
import org.mapstruct.Mapper;

@Mapper
public interface InsuranceMapper {

    InsuranceDTO toDto(Insurance entity);

    Insurance fromDto(InsuranceDTO dto);
}
