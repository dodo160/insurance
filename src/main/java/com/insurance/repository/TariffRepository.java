package com.insurance.repository;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.model.Tariff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends CrudRepository<Tariff, Long> {

    Tariff getTariffByInsuranceTypeAndPacketAndActiveTrue(final InsuranceType insuranceType, final Packet packet);
}
