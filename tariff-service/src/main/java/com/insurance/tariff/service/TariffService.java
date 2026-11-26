package com.insurance.tariff.service;

import com.insurance.common.service.BasicService;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.model.Tariff;

public interface TariffService extends BasicService<Tariff, Long> {

    Tariff getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType insType, Packet packet);
}
