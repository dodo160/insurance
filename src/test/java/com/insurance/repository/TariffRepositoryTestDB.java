package com.insurance.repository;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.model.Tariff;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TariffRepositoryTestDB {

    @Autowired
    private TariffRepository tariffRepository;

    @Test
    public void testFindById(){
        final Tariff tariff = tariffRepository.findById(1L).orElse(null);
        Assert.assertNotNull(tariff);
        Assert.assertEquals(Long.valueOf(1), tariff.getId());
        Assert.assertEquals(InsuranceType.DAY, tariff.getInsuranceType());
        Assert.assertEquals(Packet.BASIC, tariff.getPacket());
        Assert.assertEquals(new BigDecimal(1.20).setScale(2, RoundingMode.HALF_UP), tariff.getPrice().setScale(2, RoundingMode.HALF_UP));
        Assert.assertNotNull(tariff.getCreatedDate());
        Assert.assertNull(tariff.getDeletedDate());
        Assert.assertNull(tariff.getLastUpdatedDate());
    }

    @Test
    public void saveTest(){
        final Tariff newTariff = TestUtils.buildTariff(InsuranceType.DAY);
        newTariff.setId(null);
        newTariff.setPrice(new BigDecimal(30));

        final Tariff savedTariff = tariffRepository.save(newTariff);

        Assert.assertNotNull(savedTariff);
        Assert.assertEquals(newTariff.getId(),savedTariff.getId());
        Assert.assertEquals(newTariff.getInsuranceType(), savedTariff.getInsuranceType());
        Assert.assertEquals(newTariff.getPacket(), savedTariff.getPacket());
        Assert.assertEquals(newTariff.getPrice(), savedTariff.getPrice());
    }

    @Test
    public void findAllTest(){
        final Set<Tariff> tariffs = new HashSet<>();
        tariffRepository.findAll().forEach(tariffs::add);

        Assert.assertFalse(tariffs.isEmpty());
        Assert.assertEquals(6, tariffs.size());
    }

    @Test
    public void updateTest(){
        final Tariff tariff = tariffRepository.findById(1L).orElse(null);
        Assert.assertNotNull(tariff);

        tariff.setPrice(new BigDecimal(1.50));

        final Tariff savedTariff = tariffRepository.save(tariff);
        Assert.assertNotNull(savedTariff);
        Assert.assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), savedTariff.getPrice().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void getTariffInsuranceTypeAndPacketTest(){
        final Tariff tariffDB = tariffRepository.getTariffByInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC);
        Assert.assertNotNull(tariffDB);
        Assert.assertEquals(InsuranceType.DAY, tariffDB.getInsuranceType());
        Assert.assertEquals(Packet.BASIC, tariffDB.getPacket());
        Assert.assertEquals(new BigDecimal(1.20).setScale(2, RoundingMode.HALF_UP), tariffDB.getPrice().setScale(2, RoundingMode.HALF_UP));
    }
}
