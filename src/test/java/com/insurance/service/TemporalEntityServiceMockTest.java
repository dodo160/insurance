package com.insurance.service;

import com.insurance.model.TemporalEntity;
import com.insurance.repository.TemporalEntityRepository;
import com.insurance.xml.xmlvalidator.XmlValidatorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildTemporalEntity;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TemporalEntityServiceMockTest {

    private TemporalEntityServiceImpl temporalEntityService;

    @Mock
    private TemporalEntityRepository temporalEntityRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        temporalEntityService = new TemporalEntityServiceImpl(temporalEntityRepository, new XmlValidatorImpl(), Set.of());
    }

    @Test
    public void findAllTest() {
        when(temporalEntityRepository.findAll()).thenReturn(Set.of(buildTemporalEntity()));

        final Set<TemporalEntity> result = temporalEntityService.findAll();

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

    @Test(expected = ValidationException.class)
    public void softDeleteByIdTest() throws ValidationException {
        temporalEntityService.softDeleteById(1L);
    }

    @Test(expected = ValidationException.class)
    public void updateTest() throws ValidationException {
        final TemporalEntity temporalEntity = buildTemporalEntity();
        temporalEntityService.update(temporalEntity);
    }
}
