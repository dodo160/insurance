package com.insurance.service;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.exception.NotFoundException;
import com.insurance.model.Tariff;
import com.insurance.repository.TariffRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildTariff;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TariffServiceTestIT {

    @Autowired
    private TariffService tariffService;

    @MockBean
    private TariffRepository tariffRepository;

    @Test
    public void findAllTest() {
        when(tariffRepository.findAll()).thenReturn(Set.of(new Tariff()));

        final Set<Tariff> result = tariffService.findAll();

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        final Tariff tariff = new Tariff();
        tariff.setId(1l);
        when(tariffRepository.findById(1l)).thenReturn(Optional.of(tariff));

        final Tariff result = tariffService.findById(1l);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest() {
        tariffService.deleteById(1L);
        verify(tariffRepository, times(1)).deleteById(1L);
    }

    @Test
    public void softDeleteByIdTest() {
        final Tariff tariff = buildTariff(InsuranceType.DAY);
        when(tariffRepository.findById(1L)).thenReturn(Optional.of(tariff));

        tariffService.softDeleteById(1L);

        verify(tariffRepository, times(1)).save(tariff);
    }

    @Test(expected = NotFoundException.class)
    public void softDeleteByIdNullInsuranceTest() {
        when(tariffRepository.findById(1L)).thenReturn(Optional.empty());

        tariffService.softDeleteById(1L);
    }

    @Test
    public void addTest() {
        final Tariff tariff = buildTariff(InsuranceType.DAY);

        tariffService.add(tariff);

        verify(tariffRepository, times(1)).save(tariff);
    }

    @Test
    public void updateTest() {
        final Tariff tariff = buildTariff(InsuranceType.YEAR);
        tariff.setPacket(Packet.EXTRA);
        tariff.setPrice(new BigDecimal(49.00));

        when(tariffRepository.findById(1L)).thenReturn(Optional.of(buildTariff(InsuranceType.DAY)));

        final ArgumentCaptor<Tariff> tariffArgumentCaptor = ArgumentCaptor.forClass(Tariff.class);

        try {
            tariffService.update(tariff);
        } catch (NotFoundException nfe) {
            Assert.fail();
        }

        verify(tariffRepository, times(1)).save(tariffArgumentCaptor.capture());
        final Tariff tariffArgumentCaptorValue = tariffArgumentCaptor.getValue();
        Assert.assertEquals(tariff.getInsuranceType(), tariffArgumentCaptorValue.getInsuranceType());
        Assert.assertEquals(tariff.getPacket(), tariffArgumentCaptorValue.getPacket());
        Assert.assertEquals(tariff.getPrice(), tariffArgumentCaptorValue.getPrice());

    }

    @Test
    public void updateTariffDoesntExistTest() {
        when(tariffRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            tariffService.update(buildTariff(InsuranceType.DAY));
            Assert.fail();
        } catch (NotFoundException nfe) {
            Assert.assertEquals("Tariff not found", nfe.getMessage());
        }
    }

    @Test
    public void getTariffInsuranceTypeAndPacketTest() {
        when(tariffRepository.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC)).thenReturn(buildTariff(InsuranceType.DAY));

        final Tariff tariff = tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC);

        Assert.assertNotNull(tariff);
    }

}
