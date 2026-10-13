package com.insurance.temporal.service;

import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.temporal.repository.TemporalEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.insurance.utils.TestUtils.buildTemporalEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TemporalEntityServiceIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private TemporalEntityServiceImpl temporalEntityService;

    @MockBean
    private TemporalEntityRepository temporalEntityRepository;

    @Test
    public void findAllTest() {
        when(temporalEntityRepository.findAll()).thenReturn(Set.of(buildTemporalEntity()));

        final List<TemporalEntity> result = temporalEntityService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(temporalEntityRepository.findById(1l)).thenReturn(Optional.of(buildTemporalEntity()));

        final TemporalEntity result = temporalEntityService.findById(1l);

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest() {
        temporalEntityService.deleteById(1L);
        verify(temporalEntityRepository, times(1)).deleteById(1L);
    }

    @Test()
    public void softDeleteByIdTest() {
        try {
            temporalEntityService.softDeleteById(1L);
        } catch (UnsupportedOperationException e) {
            assertEquals("Not supported action.", e.getMessage());
        }
    }

    @Test()
    public void updateTest() {
        final TemporalEntity temporalEntity = buildTemporalEntity();
        try {
            temporalEntityService.update(temporalEntity.getId(), temporalEntity);
        } catch (UnsupportedOperationException e) {
            assertEquals("Not supported action.", e.getMessage());
        }
    }
}
