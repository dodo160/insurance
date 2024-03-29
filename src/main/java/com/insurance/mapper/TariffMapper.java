package com.insurance.mapper;

import com.insurance.model.Tariff;
import com.insurance.modeldto.TariffDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = InsuranceMapper.class)
public interface TariffMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "insuranceType", source = "entity.insuranceType"),
            @Mapping(target = "packet", source = "entity.packet"),
            @Mapping(target = "price", source = "entity.price"),
    })
    TariffDTO tariffToTariffDTO(Tariff entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "insuranceType", source = "dto.insuranceType"),
            @Mapping(target = "packet", source = "dto.packet"),
            @Mapping(target = "price", source = "dto.price"),
    })
    Tariff tariffDTOtoTariff(TariffDTO dto);
}
