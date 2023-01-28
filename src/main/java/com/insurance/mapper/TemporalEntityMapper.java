package com.insurance.mapper;

import com.insurance.model.TemporalEntity;
import com.insurance.modeldto.TemporalEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = UserMapper.class)
public interface TemporalEntityMapper {

    @Mappings(value = {
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "user", source = "entity.user"),
            @Mapping(target = "entityClass", source = "entity.entityClass"),
            @Mapping(target = "mediaType", source = "entity.mediaType"),
            @Mapping(target = "entity", source = "entity.entity"),
    })
    TemporalEntityDTO temporalEntityToTemporalEntityDTO(TemporalEntity entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "user", source = "dto.user"),
            @Mapping(target = "entityClass", source = "dto.entityClass"),
            @Mapping(target = "mediaType", source = "dto.mediaType"),
            @Mapping(target = "entity", source = "dto.entity"),
    })
    TemporalEntity temporalEntityDTOtoTemporalEntity(TemporalEntityDTO dto);
}
