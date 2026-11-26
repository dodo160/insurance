package com.insurance.utils;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.model.Tariff;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestUtils {

    private TestUtils() {
    }
    public static Tariff buildTariff(final InsuranceType insuranceType) {
        final Tariff tariff = new Tariff();
        tariff.setId(1l);
        tariff.setInsuranceType(insuranceType);
        tariff.setPacket(Packet.BASIC);
        tariff.setPrice(insuranceType == InsuranceType.YEAR ? new BigDecimal(39.0).setScale(2, RoundingMode.HALF_UP) : new BigDecimal(1.2).setScale(2, RoundingMode.HALF_UP));
        return tariff;
    }

    public static TariffDTO buildTariffDTO(final InsuranceType insuranceType) {
        return TariffDTO.builder().id(1L).insuranceType(insuranceType).packet(Packet.BASIC)
                .price(insuranceType == InsuranceType.YEAR ? new BigDecimal(39.0).setScale(2, RoundingMode.HALF_UP)
                        : new BigDecimal(1.2).setScale(2, RoundingMode.HALF_UP)).build();
    }
}

