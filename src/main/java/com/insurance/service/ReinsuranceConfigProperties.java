package com.insurance.service;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.ReinsuranceType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Component
@Validated
@PropertySource("classpath:reinsurance.properties")
@ConfigurationProperties(prefix = "ext.param")
public class ReinsuranceConfigProperties {

    @Valid
    @NotNull
    private PeriodParameters day;

    @Valid
    @NotNull
    private PeriodParameters year;

    public PeriodParameters getDay() {
        return day;
    }

    public void setDay(PeriodParameters day) {
        this.day = day;
    }

    public PeriodParameters getYear() {
        return year;
    }

    public void setYear(PeriodParameters year) {
        this.year = year;
    }

    public static class PeriodParameters {

        @NotNull
        private BigDecimal storno;

        @NotNull
        private BigDecimal sportsActivity;

        public PeriodParameters() {
        }

        public BigDecimal getStorno() {
            return storno;
        }

        public void setStorno(BigDecimal storno) {
            this.storno = storno;
        }

        public BigDecimal getSportsActivity() {
            return sportsActivity;
        }

        public void setSportsActivity(BigDecimal sportsActivity) {
            this.sportsActivity = sportsActivity;
        }
    }

    public BigDecimal get(InsuranceType period, ReinsuranceType activity) {
        final PeriodParameters params = switch (period) {
            case DAY -> day;
            case YEAR -> year;
            default -> throw new IllegalArgumentException("Invalid period parameter");
        };

        switch (activity) {
            case STORNO:
                return params.getStorno();
            case SPORTS_ACTIVITY:
                return params.getSportsActivity();
            default:
                throw new IllegalArgumentException("Invalid activity");
        }
    }
}
