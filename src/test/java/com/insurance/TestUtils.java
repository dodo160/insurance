package com.insurance;

import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.dto.ReinsuranceDTO;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.enums.ReinsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.model.Reinsurance;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.model.Tariff;
import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.enums.UserType;
import com.insurance.user.model.Client;
import com.insurance.user.model.Employee;
import com.insurance.user.model.User;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class TestUtils {

    private TestUtils() {
    }

    public static Insurance buildInsurance(final InsuranceType insuranceType) {
        final Tariff tariff = buildTariff(insuranceType);
        final User user = buildUser(UserType.CLIENT);

        final Reinsurance reinsuranceStorno = buildReinsurance(ReinsuranceType.STORNO);

        final Reinsurance reinsuranceSportsActivity = buildReinsurance(ReinsuranceType.SPORTS_ACTIVITY);

        final Insurance insurance = new Insurance();
        insurance.setId(1l);
        insurance.setPerson(2);
        insurance.setStartDate(LocalDate.now());
        insurance.setEndDate(insuranceType == InsuranceType.YEAR ? LocalDate.now().plusYears(1) : LocalDate.now().plusDays(2));
        insurance.setTariff(tariff);
        insurance.setReinsurances(List.of(reinsuranceStorno, reinsuranceSportsActivity));
        insurance.setUser(user);
        return insurance;
    }

    public static Tariff buildTariff(final InsuranceType insuranceType) {
        final Tariff tariff = new Tariff();
        tariff.setId(1l);
        tariff.setInsuranceType(insuranceType);
        tariff.setPacket(Packet.BASIC);
        tariff.setPrice(insuranceType == InsuranceType.YEAR ? new BigDecimal(39.0).setScale(2, RoundingMode.HALF_UP) : new BigDecimal(1.2).setScale(2, RoundingMode.HALF_UP));
        return tariff;
    }

    public static User buildUser(final UserType userType) {
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

    public static Reinsurance buildReinsurance(final ReinsuranceType reinsuranceType) {
        final Reinsurance reinsurance = new Reinsurance();
        switch (reinsuranceType) {
            case STORNO:
                reinsurance.setId(1l);
            case SPORTS_ACTIVITY:
                reinsurance.setId(2l);
        }
        reinsurance.setReinsuranceType(reinsuranceType);
        return reinsurance;
    }

    public static TemporalEntity buildTemporalEntity() {
        final String xmlString = "XML_STRING";
        final TemporalEntity temporalEntity = new TemporalEntity();
        temporalEntity.setId(1L);
        temporalEntity.setEntityClass(Insurance.class.getSimpleName());
        temporalEntity.setMediaType(MediaType.APPLICATION_XML_VALUE);
        temporalEntity.setEntity(xmlString);
        temporalEntity.setUser(buildUser(UserType.CLIENT));
        return temporalEntity;
    }

    public static InsuranceDTO buildInsuranceDto(final InsuranceType insuranceType) {
        final TariffDTO tariff = buildTariffDTO(insuranceType);
        final UserDTO user = buildUserDTO(UserType.CLIENT);

        final ReinsuranceDTO reinsuranceStorno = ReinsuranceDTO.builder().id(1L).reinsuranceType(ReinsuranceType.STORNO).build();
        final ReinsuranceDTO reinsuranceSportsActivity = ReinsuranceDTO.builder().id(2L).reinsuranceType(ReinsuranceType.SPORTS_ACTIVITY).build();

        return InsuranceDTO.builder().id(1l).person(2).startDate(LocalDate.now())
                .endDate(insuranceType == InsuranceType.YEAR ? LocalDate.now().plusYears(1) : LocalDate.now().plusDays(2))
                .reinsurances(List.of(reinsuranceStorno, reinsuranceSportsActivity)).user(user).tariff(tariff).build();
    }

    public static TariffDTO buildTariffDTO(final InsuranceType insuranceType) {
        return TariffDTO.builder().id(1L).insuranceType(insuranceType).packet(Packet.BASIC)
                .price(insuranceType == InsuranceType.YEAR ? new BigDecimal(39.0).setScale(2, RoundingMode.HALF_UP)
                        : new BigDecimal(1.2).setScale(2, RoundingMode.HALF_UP)).build();
    }

    public static UserDTO buildUserDTO(final UserType userType) {
        return UserDTO.builder().id(1L).firstName("FIRST_NAME").lastName("LAST_NAME")
                .address("ADDRESS").city("CITY").postCode("1111").identityId("2222")
                .userType(userType).build();
    }

    public static TemporalEntityDTO buildTemporalEntityDTO() {
        final String xmlString = "XML_STRING";
        return TemporalEntityDTO.builder().id(1L).entityClass(Insurance.class.getSimpleName())
                .mediaType(MediaType.APPLICATION_XML_VALUE).entity(xmlString).user(buildUserDTO(UserType.CLIENT)).build();
    }
}
