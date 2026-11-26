package com.insurance.insurance.service.calculator;

import com.insurance.common.exception.NotFoundException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.service.strategy.InsurancePriceStrategy;
import com.insurance.tariff.client.TariffGateway;
import com.insurance.tariff.model.TariffEmbedded;
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
    private final TariffGateway tariffGateway;
    private final Map<InsuranceType, InsurancePriceStrategy> insurancePriceStrategies;

    public InsurancePriceCalculator(final ReinsuranceCalculator reinsuranceCalculator, final TariffGateway tariffGateway, final Set<InsurancePriceStrategy> strategy) {
        this.reinsuranceCalculator = reinsuranceCalculator;
        this.tariffGateway = tariffGateway;
        this.insurancePriceStrategies = strategy.stream().collect(Collectors.toMap(InsurancePriceStrategy::getInsuranceType, Function.identity()));
    }

    public BigDecimal calculateInsurance(final Insurance insurance) {
        return calculateInsurance(insurance, resolveTariff(insurance));
    }

    public BigDecimal calculateAndApplyTariff(final Insurance insurance) {
        final TariffEmbedded tariff = resolveTariff(insurance);
        final BigDecimal price = calculateInsurance(insurance, tariff);
        insurance.setTariff(tariff);
        return price;
    }

    private TariffEmbedded resolveTariff(final Insurance insurance) {
        final TariffEmbedded tariff = tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(insurance.getTariff().getInsuranceType(), insurance.getTariff().getPacket());
        if (Objects.isNull(tariff)) {
            throw new NotFoundException("Tariff doesn't exist");
        }
        return tariff;
    }

    private BigDecimal calculateInsurance(final Insurance insurance, final TariffEmbedded tariff) {

        final BigDecimal reinsurancePrice = reinsuranceCalculator.calculateReinsurance(insurance.getReinsurances(), insurance.getTariff().getInsuranceType());

        return insurancePriceStrategies.get(insurance.getTariff().getInsuranceType()).calculateInsurance(insurance, reinsurancePrice, tariff.getPrice());
    }
}
