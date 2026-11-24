package com.insurance.temporal.mapper;

import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.user.mapper.UserMapper;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TemporalEntityMapperTest {

    private TemporalEntityMapper temporalEntityMapper;

    @BeforeEach
    public void setUpTest() {
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);

        temporalEntityMapper = new TemporalEntityMapperImpl(userMapper);
    }

    @Test
    public void fromDtoTest() {
        final TemporalEntityDTO temporalEntityDTO = TestUtils.buildTemporalEntityDTO();

        final TemporalEntity temporalEntity = temporalEntityMapper.fromDto(temporalEntityDTO);

        assertNotNull(temporalEntity);
        assertEquals(temporalEntityDTO.getId(), temporalEntity.getId());
        assertEquals(temporalEntityDTO.getEntity(), temporalEntity.getEntity());
        assertEquals(temporalEntityDTO.getEntityClass(), temporalEntity.getEntityClass());
        assertEquals(temporalEntityDTO.getMediaType(), temporalEntity.getMediaType());
        assertNotNull(temporalEntityDTO.getUser());
        assertEquals(temporalEntityDTO.getUser().getId(), temporalEntity.getUser().getId());
        assertEquals(temporalEntityDTO.getUser().getFirstName(), temporalEntity.getUser().getFirstName());
        assertEquals(temporalEntityDTO.getUser().getLastName(), temporalEntity.getUser().getLastName());
        assertEquals(temporalEntityDTO.getUser().getAddress(), temporalEntity.getUser().getAddress());
        assertEquals(temporalEntityDTO.getUser().getCity(), temporalEntity.getUser().getCity());
        assertEquals(temporalEntityDTO.getUser().getPostCode(), temporalEntity.getUser().getPostCode());
        assertEquals(temporalEntityDTO.getUser().getIdentityId(), temporalEntity.getUser().getIdentityId());
    }

    @Test
    public void toDtoTest() {
        final TemporalEntity temporalEntity = TestUtils.buildTemporalEntity();

        final TemporalEntityDTO temporalEntityDTO = temporalEntityMapper.toDto(temporalEntity);

        assertNotNull(temporalEntityDTO);
        assertEquals(temporalEntity.getId(), temporalEntityDTO.getId());
        assertEquals(temporalEntity.getEntity(), temporalEntityDTO.getEntity());
        assertEquals(temporalEntity.getEntityClass(), temporalEntityDTO.getEntityClass());
        assertEquals(temporalEntity.getMediaType(), temporalEntityDTO.getMediaType());
        assertNotNull(temporalEntity.getUser());
        assertEquals(temporalEntity.getUser().getId(), temporalEntityDTO.getUser().getId());
        assertEquals(temporalEntity.getUser().getFirstName(), temporalEntityDTO.getUser().getFirstName());
        assertEquals(temporalEntity.getUser().getLastName(), temporalEntityDTO.getUser().getLastName());
        assertEquals(temporalEntity.getUser().getAddress(), temporalEntityDTO.getUser().getAddress());
        assertEquals(temporalEntity.getUser().getCity(), temporalEntityDTO.getUser().getCity());
        assertEquals(temporalEntity.getUser().getPostCode(), temporalEntityDTO.getUser().getPostCode());
        assertEquals(temporalEntity.getUser().getIdentityId(), temporalEntityDTO.getUser().getIdentityId());
    }
}
