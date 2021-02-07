package com.insurance.mapper;

import com.insurance.model.Reinsurance;
import com.insurance.modeldto.ReinsuranceDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ReinsuranceMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "insurance", source = "entity.insurance"),
            @Mapping(target = "reinsuranceType", source = "entity.reinsuranceType")

    })
    ReinsuranceDTO reinsuranceToReinsuranceDTO(Reinsurance entity, @Context CycleAvoidingMappingContext context);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "insurance", source = "dto.insurance"),
            @Mapping(target = "reinsuranceType", source = "dto.reinsuranceType")
                })
    Reinsurance reinsuranceDTOtoReinsurance(ReinsuranceDTO dto, @Context CycleAvoidingMappingContext context);
}
