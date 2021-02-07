package com.insurance.service;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.model.Tariff;
import com.insurance.repository.TariffRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildTariff;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TariffServiceMockTest {

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Mock
    private TariffRepository tariffRepository;


    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest(){
        when(tariffRepository.findAll()).thenReturn(Set.of(new Tariff()));

        final Set<Tariff> result =tariffService.findAll();

        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void findByIdTest(){
        final Tariff tariff = new Tariff();
        tariff.setId(1l);
        when(tariffRepository.findById(1l)).thenReturn(Optional.of(tariff));

        final Tariff result = tariffService.findById(1l);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest(){
        tariffService.deleteById(1L);
        verify(tariffRepository,times(1)).deleteById(1L);
    }

    @Test
    public void softDeleteByIdTest(){
        final Tariff tariff = buildTariff(InsuranceType.DAY);
        when(tariffRepository.findById(1L)).thenReturn(Optional.of(tariff));

        tariffService.softDeleteById(1L);

        Assert.assertNotNull(tariff.getDeletedDate());
        verify(tariffRepository, times(1)).save(tariff);
    }

    @Test
    public void softDeleteByIdNullInsuranceTest(){
        when(tariffRepository.findById(1L)).thenReturn(Optional.empty());

        tariffService.softDeleteById(1L);

        verify(tariffRepository, times(0)).save(any());
    }

    @Test
    public void addTest(){
        final Tariff tariff = buildTariff(InsuranceType.DAY);

        tariffService.add(tariff);

        verify(tariffRepository,times(1)).save(tariff);
    }

    @Test
    public void updateTest(){
        final Tariff tariff = buildTariff(InsuranceType.YEAR);
        tariff.setPacket(Packet.EXTEND);
        tariff.setPrice(new BigDecimal(49.00));

        when(tariffRepository.findById(1L)).thenReturn(Optional.of(buildTariff(InsuranceType.DAY)));

        final ArgumentCaptor<Tariff> tariffArgumentCaptor = ArgumentCaptor.forClass(Tariff.class);

        try {
            tariffService.update(tariff);
        } catch (ValidationException e) {
            Assert.fail();
        }

        verify(tariffRepository,times(1)).save(tariffArgumentCaptor.capture());
        final Tariff tariffArgumentCaptorValue = tariffArgumentCaptor.getValue();
        Assert.assertEquals(tariff.getInsuranceType(), tariffArgumentCaptorValue.getInsuranceType());
        Assert.assertEquals(tariff.getPacket(), tariffArgumentCaptorValue.getPacket());
        Assert.assertEquals(tariff.getPrice(), tariffArgumentCaptorValue.getPrice());

    }

    @Test
    public void updateTariffDoesntExistTest(){
        when(tariffRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            tariffService.update(buildTariff(InsuranceType.DAY));
            Assert.fail();
        } catch (ValidationException e) {
            Assert.assertTrue("Tariff entity doesn't exist".equals(e.getMessage()));
        }
    }

    @Test
    public void getTariffInsuranceTypeAndPacketTest(){
        when(tariffRepository.getTariffByInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC)).thenReturn(buildTariff(InsuranceType.DAY));

        final Tariff tariff = tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC);

        Assert.assertNotNull(tariff);
    }

}
