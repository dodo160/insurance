package com.insurance;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.enums.ReinsuranceType;
import com.insurance.enums.UserType;
import com.insurance.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestUtils {

    private TestUtils() {
    }

    public static Insurance buildInsurance(final InsuranceType insuranceType){
        final Tariff tariff = buildTariff(insuranceType);
        final User user = buildUser(UserType.CLIENT);

        final Reinsurance reinsuranceStorno = new Reinsurance();
        reinsuranceStorno.setId(1l);
        reinsuranceStorno.setReinsuranceType(ReinsuranceType.STORNO);

        final Reinsurance reinsuranceSportsActivity = new Reinsurance();
        reinsuranceSportsActivity.setId(2l);
        reinsuranceSportsActivity.setReinsuranceType(ReinsuranceType.SPORTS_ACTIVITY);

        final Insurance insurance = new Insurance();
        insurance.setId(1l);
        insurance.setPerson(2);
        insurance.setStartDate(LocalDate.now());
        insurance.setEndDate(insuranceType == InsuranceType.YEAR ? LocalDate.now().plusYears(1) : LocalDate.now().plusDays(2));
        insurance.setTariff(tariff);
        insurance.addReinsurance(reinsuranceStorno);
        insurance.addReinsurance(reinsuranceSportsActivity);
        insurance.setUser(user);
        return insurance;
    }

    public static Tariff buildTariff(final InsuranceType insuranceType){
        final Tariff tariff = new Tariff();
        tariff.setId(1l);
        tariff.setInsuranceType(insuranceType);
        tariff.setPacket(Packet.BASIC);
        tariff.setPrice(insuranceType == InsuranceType.YEAR ? new BigDecimal(39.0): new BigDecimal(1.2));
        return tariff;
    }

    public static User buildUser(final UserType userType){
        final User user = userType == UserType.CLIENT ? new Client() : new Employee();
        user.setId(1L);
        user.setFirstName("FIRST_NAME");
        user.setLastName("LAST_NAME");
        user.setAddress("ADDRESS");
        user.setCity("CITY");
        user.setPostCode("1111");
        user.setIdentityId("2222");
        return user;
    }
}
