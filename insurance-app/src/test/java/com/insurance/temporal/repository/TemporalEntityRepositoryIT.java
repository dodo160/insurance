package com.insurance.temporal.repository;

import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.temporal.model.TemporalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static com.insurance.utils.TestUtils.buildTemporalEntity;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TemporalEntityRepositoryIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private TemporalEntityRepository temporalEntityRepository;

    @Test
    public void saveTest() {
        final TemporalEntity newTemporalEntity = buildTemporalEntity();
        newTemporalEntity.setId(null);

        final TemporalEntity savedTemporalEntity = temporalEntityRepository.save(newTemporalEntity);

        final TemporalEntity temporalEntityDB = temporalEntityRepository.findById(savedTemporalEntity.getId()).orElse(null);

        assertNotNull(temporalEntityDB);
        assertEquals(newTemporalEntity.getEntityClass(), temporalEntityDB.getEntityClass());
        assertEquals(newTemporalEntity.getMediaType(), temporalEntityDB.getMediaType());
        assertNotNull(newTemporalEntity.getUser());
        assertNotNull(newTemporalEntity.getEntity());

        final Set<TemporalEntity> temporalEntities = new HashSet<>();
        temporalEntityRepository.findAll().forEach(temporalEntities::add);

        assertFalse(temporalEntities.isEmpty());
        assertEquals(1, temporalEntities.size());
    }
}
