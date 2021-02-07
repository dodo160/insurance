package com.insurance.mapper;

import com.insurance.model.Insurance;
import com.insurance.modeldto.InsuranceDTO;
import org.mapstruct.*;

@Mapper
public interface InsuranceMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "tariff", source = "entity.tariff"),
            @Mapping(target = "user", source = "entity.user"),
            @Mapping(target = "startDate", source = "entity.startDate"),
            @Mapping(target = "endDate", source = "entity.endDate"),
            @Mapping(target = "reinsurances", source = "entity.reinsurances"),
            @Mapping(target = "person", source = "entity.person"),
            @Mapping(target = "price", source = "entity.price")
    })
    InsuranceDTO insuranceToInsuranceDTO(Insurance entity, @Context CycleAvoidingMappingContext context);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "tariff", source = "dto.tariff"),
            @Mapping(target = "user", source = "dto.user"),
            @Mapping(target = "startDate", source = "dto.startDate"),
            @Mapping(target = "endDate", source = "dto.endDate"),
            @Mapping(target = "reinsurances", source = "dto.reinsurances"),
            @Mapping(target = "person", source = "dto.person"),
            @Mapping(target = "price", source = "dto.price")

    })
    @InheritInverseConfiguration
    Insurance insuranceDTOtoInsurance(InsuranceDTO dto, @Context CycleAvoidingMappingContext context);
}
