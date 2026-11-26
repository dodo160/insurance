package com.insurance.tariff.client;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tariff-service", url = "${insurance.tariff.base-url}", path = "/tariff")
public interface TariffClient {

    @GetMapping(value = "/tariffByInsuranceTypeAndPacket", produces = MediaType.APPLICATION_JSON_VALUE)
    TariffDTO findActiveTariff(@RequestParam("insuranceType") InsuranceType insuranceType,
                               @RequestParam("packet") Packet packet);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void add(@RequestBody TariffDTO tariff);
}
