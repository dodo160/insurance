package com.insurance.tariff.repository;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.model.Tariff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends CrudRepository<Tariff, Long> {

    Tariff getTariffByInsuranceTypeAndPacketAndActiveTrue(final InsuranceType insuranceType, final Packet packet);
}
