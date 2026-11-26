package com.insurance.tariff.client;

import com.insurance.common.exception.DuplicateException;
import com.insurance.common.exception.NotFoundException;
import com.insurance.common.exception.RequestMismatchException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.mapper.TariffEmbeddedMapper;
import com.insurance.tariff.model.TariffEmbedded;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class TariffGateway {

    private final TariffClient client;
    private final TariffEmbeddedMapper mapper;

    public TariffGateway(final TariffClient client, final TariffEmbeddedMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public TariffEmbedded getTariffByInsuranceTypeAndPacketAndActiveTrue(final InsuranceType type, final Packet packet) {
        try {
            return mapper.fromDto(client.findActiveTariff(type, packet));
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Tariff not found");
        }
    }

    public void add(final TariffEmbedded tariff) {
        try {
            client.add(mapper.toDto(tariff));
        } catch (FeignException.Conflict e) {
            throw new DuplicateException("An active tariff already exists for this insurance type and packet");
        } catch (FeignException.BadRequest e) {
            throw new RequestMismatchException("Tariff validation failed");
        }
    }
}
