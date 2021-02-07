package com.insurance.service;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.model.Tariff;

public interface TariffService extends BasicService<Tariff,Long> {

	Tariff getTariffInsuranceTypeAndPacket(InsuranceType insType, Packet packet);
}
