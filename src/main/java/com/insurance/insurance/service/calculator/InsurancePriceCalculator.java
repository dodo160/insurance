package com.insurance.insurance.service.calculator;

import com.insurance.common.exception.NotFoundException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.service.strategy.InsurancePriceStrategy;
import com.insurance.tariff.model.Tariff;
import com.insurance.tariff.service.TariffService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InsurancePriceCalculator {

    private final ReinsuranceCalculator reinsuranceCalculator;
    private final TariffService tariffService;
    private final Map<InsuranceType, InsurancePriceStrategy> insurancePriceStrategies;

    public InsurancePriceCalculator(final ReinsuranceCalculator reinsuranceCalculator, final TariffService tariffService, final Set<InsurancePriceStrategy> strategy) {
        this.reinsuranceCalculator = reinsuranceCalculator;
        this.tariffService = tariffService;
        this.insurancePriceStrategies = strategy.stream().collect(Collectors.toMap(InsurancePriceStrategy::getInsuranceType, Function.identity()));
    }

    public BigDecimal calculateInsurance(final Insurance insurance) {
        final Tariff tariffDB = tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(insurance.getTariff().getInsuranceType(), insurance.getTariff().getPacket());
        if (Objects.isNull(tariffDB)) {
            throw new NotFoundException("Tarif doesn't exist");
        }
        final BigDecimal tariffPrice = tariffDB.getPrice();

        final BigDecimal reinsurancePrice = reinsuranceCalculator.calculateReinsurance(insurance.getReinsurances(), insurance.getTariff().getInsuranceType());

        return insurancePriceStrategies.get(insurance.getTariff().getInsuranceType()).calculateInsurance(insurance, reinsurancePrice, tariffPrice);
    }
}
