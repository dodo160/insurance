package com.insurance.temporal.service;

import com.insurance.temporal.model.TemporalEntity;
import com.insurance.temporal.repository.TemporalEntityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildTemporalEntity;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TemporalEntityServiceIT {

    @Autowired
    private TemporalEntityServiceImpl temporalEntityService;

    @MockBean
    private TemporalEntityRepository temporalEntityRepository;

    @Test
    public void findAllTest() {
        when(temporalEntityRepository.findAll()).thenReturn(Set.of(buildTemporalEntity()));

        final List<TemporalEntity> result = temporalEntityService.findAll();

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(temporalEntityRepository.findById(1l)).thenReturn(Optional.of(buildTemporalEntity()));

        final TemporalEntity result = temporalEntityService.findById(1l);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest() {
        temporalEntityService.deleteById(1L);
        verify(temporalEntityRepository, times(1)).deleteById(1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void softDeleteByIdTest() {
        temporalEntityService.softDeleteById(1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateTest() {
        final TemporalEntity temporalEntity = buildTemporalEntity();
        temporalEntityService.update(temporalEntity.getId(), temporalEntity);
    }
}
