package com.insurance.tariff.repository;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.model.Tariff;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TariffRepositoryIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private TariffRepository tariffRepository;

    @Test
    public void testFindById() {
        final Tariff tariff = tariffRepository.findById(1L).orElse(null);
        assertNotNull(tariff);
        assertEquals(Long.valueOf(1), tariff.getId());
        assertEquals(InsuranceType.DAY, tariff.getInsuranceType());
        assertEquals(Packet.BASIC, tariff.getPacket());
        assertEquals(new BigDecimal("1.20"), tariff.getPrice());
        assertNotNull(tariff.getCreatedDate());
        assertNull(tariff.getDeletedDate());
        assertNull(tariff.getLastUpdatedDate());
    }

    @Test
    public void saveTest() {
        final Tariff newTariff = TestUtils.buildTariff(InsuranceType.DAY);
        newTariff.setId(null);
        newTariff.setPrice(new BigDecimal("30"));

        final Tariff savedTariff = tariffRepository.save(newTariff);

        assertNotNull(savedTariff);
        assertEquals(newTariff.getId(), savedTariff.getId());
        assertEquals(newTariff.getInsuranceType(), savedTariff.getInsuranceType());
        assertEquals(newTariff.getPacket(), savedTariff.getPacket());
        assertEquals(newTariff.getPrice(), savedTariff.getPrice());
    }

    @Test
    public void findAllTest() {
        final List<Tariff> tariffs = new ArrayList<>();
        tariffRepository.findAll().forEach(tariffs::add);

        assertFalse(tariffs.isEmpty());
        assertEquals(6, tariffs.size());
    }

    @Test
    public void updateTest() {
        final Tariff tariff = tariffRepository.findById(1L).orElse(null);
        assertNotNull(tariff);

        tariff.setPrice(new BigDecimal("1.50"));

        final Tariff savedTariff = tariffRepository.save(tariff);
        assertNotNull(savedTariff);
        assertEquals(new BigDecimal("1.50"), savedTariff.getPrice());
    }

    @Test
    public void getTariffInsuranceTypeAndPacketTest() {
        final Tariff tariffDB = tariffRepository.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC);
        assertNotNull(tariffDB);
        assertEquals(InsuranceType.DAY, tariffDB.getInsuranceType());
        assertEquals(Packet.BASIC, tariffDB.getPacket());
        assertEquals(new BigDecimal("1.20").setScale(2, RoundingMode.HALF_UP), tariffDB.getPrice());
    }
}
