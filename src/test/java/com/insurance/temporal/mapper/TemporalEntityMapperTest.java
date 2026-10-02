package com.insurance.temporal.mapper;

import com.insurance.TestUtils;
import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.model.TemporalEntity;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class TemporalEntityMapperTest {

    private final TemporalEntityMapper temporalEntityMapper = Mappers.getMapper(TemporalEntityMapper.class);

    @Test
    public void fromDtoTest() {
        final TemporalEntityDTO temporalEntityDTO = TestUtils.buildTemporalEntityDTO();

        final TemporalEntity temporalEntity = temporalEntityMapper.fromDto(temporalEntityDTO);

        Assert.assertNotNull(temporalEntity);
        Assert.assertEquals(temporalEntityDTO.getId(), temporalEntity.getId());
        Assert.assertEquals(temporalEntityDTO.getEntity(), temporalEntity.getEntity());
        Assert.assertEquals(temporalEntityDTO.getEntityClass(), temporalEntity.getEntityClass());
        Assert.assertEquals(temporalEntityDTO.getMediaType(), temporalEntity.getMediaType());
        Assert.assertNotNull(temporalEntityDTO.getUser());
        Assert.assertEquals(temporalEntityDTO.getUser().getId(), temporalEntity.getUser().getId());
        Assert.assertEquals(temporalEntityDTO.getUser().getFirstName(), temporalEntity.getUser().getFirstName());
        Assert.assertEquals(temporalEntityDTO.getUser().getLastName(), temporalEntity.getUser().getLastName());
        Assert.assertEquals(temporalEntityDTO.getUser().getAddress(), temporalEntity.getUser().getAddress());
        Assert.assertEquals(temporalEntityDTO.getUser().getCity(), temporalEntity.getUser().getCity());
        Assert.assertEquals(temporalEntityDTO.getUser().getPostCode(), temporalEntity.getUser().getPostCode());
        Assert.assertEquals(temporalEntityDTO.getUser().getIdentityId(), temporalEntity.getUser().getIdentityId());
    }

    @Test
    public void toDtoTest() {
        final TemporalEntity temporalEntity = TestUtils.buildTemporalEntity();

        final TemporalEntityDTO temporalEntityDTO = temporalEntityMapper.toDto(temporalEntity);

        Assert.assertNotNull(temporalEntityDTO);
        Assert.assertEquals(temporalEntity.getId(), temporalEntityDTO.getId());
        Assert.assertEquals(temporalEntity.getEntity(), temporalEntityDTO.getEntity());
        Assert.assertEquals(temporalEntity.getEntityClass(), temporalEntityDTO.getEntityClass());
        Assert.assertEquals(temporalEntity.getMediaType(), temporalEntityDTO.getMediaType());
        Assert.assertNotNull(temporalEntity.getUser());
        Assert.assertEquals(temporalEntity.getUser().getId(), temporalEntityDTO.getUser().getId());
        Assert.assertEquals(temporalEntity.getUser().getFirstName(), temporalEntityDTO.getUser().getFirstName());
        Assert.assertEquals(temporalEntity.getUser().getLastName(), temporalEntityDTO.getUser().getLastName());
        Assert.assertEquals(temporalEntity.getUser().getAddress(), temporalEntityDTO.getUser().getAddress());
        Assert.assertEquals(temporalEntity.getUser().getCity(), temporalEntityDTO.getUser().getCity());
        Assert.assertEquals(temporalEntity.getUser().getPostCode(), temporalEntityDTO.getUser().getPostCode());
        Assert.assertEquals(temporalEntity.getUser().getIdentityId(), temporalEntityDTO.getUser().getIdentityId());
    }
}
