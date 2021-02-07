package com.insurance.repository;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.model.Tariff;
import org.springframework.data.repository.CrudRepository;

public interface TariffRepository extends CrudRepository<Tariff,Long> {

//    @Query("SELECT tariff FROM Tariff as tariff where tariff.insuranceType = :insuranceType AND tariff.packet = :packet")
//    Tariff getTariffByInsuranceTypeAndPacket(@Param("insuranceType") InsuranceType insuranceType, @Param("packet") Packet packet);
    Tariff getTariffByInsuranceTypeAndPacket(final InsuranceType insuranceType, final Packet packet);

}
