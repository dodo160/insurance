package com.insurance.repository;

import com.insurance.model.TemporalEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static com.insurance.TestUtils.buildTemporalEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TemporalEntityRepositoryTestDB {

    @Autowired
    private TemporalEntityRepository temporalEntityRepository;

    @Test
    public void saveTest(){
        final TemporalEntity newTemporalEntity = buildTemporalEntity();
        newTemporalEntity.setId(null);

        final TemporalEntity savedTemporalEntity = temporalEntityRepository.save(newTemporalEntity);

        final TemporalEntity temporalEntityDB = temporalEntityRepository.findById(savedTemporalEntity.getId()).orElse(null);

        Assert.assertNotNull(temporalEntityDB);
        Assert.assertEquals(newTemporalEntity.getEntityClass(), temporalEntityDB.getEntityClass());
        Assert.assertEquals(newTemporalEntity.getMediaType(), temporalEntityDB.getMediaType());
        Assert.assertNotNull(newTemporalEntity.getUser());
        Assert.assertNotNull(newTemporalEntity.getEntity());

        final Set<TemporalEntity> temporalEntities = new HashSet<>();
        temporalEntityRepository.findAll().forEach(temporalEntities::add);

        Assert.assertFalse(temporalEntities.isEmpty());
        Assert.assertEquals(1, temporalEntities.size());
    }
}
