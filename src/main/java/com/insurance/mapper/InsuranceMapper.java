package com.insurance.mapper;

import com.insurance.model.Insurance;
import com.insurance.modeldto.InsuranceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface InsuranceMapper {

    InsuranceDTO toDto(Insurance entity);

    Insurance fromDto(InsuranceDTO dto);
}
