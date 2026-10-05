package com.insurance.tariff.api;

import com.insurance.common.exception.RequestMismatchException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.tariff.service.TariffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tariff")
public class TariffRestController {

    private final TariffService tariffService;

    private final TariffMapper tariffMapper;

    public TariffRestController(TariffService tariffService, TariffMapper tariffMapper) {
        this.tariffService = tariffService;
        this.tariffMapper = tariffMapper;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.findById(id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TariffDTO>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.findAll().stream().map(tariffMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> addTariff(@RequestBody final TariffDTO tariffDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffMapper.toDto(tariffService.add(tariffMapper.fromDto(tariffDTO))));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> updateTariff(@PathVariable final Long id, @RequestBody final TariffDTO tariffDTO) {
        if (Objects.nonNull(tariffDTO.getId()) && !id.equals(tariffDTO.getId())) {
            throw new RequestMismatchException(
                    "Tariff ID in path does not match user ID in request body");
        }
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.update(id, tariffMapper.fromDto(tariffDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTariff(@PathVariable("id") final Long id) {
        tariffService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/tariffByInsuranceTypeAndPacket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> getTariffByInsuranceTypeAndPacket(@RequestParam InsuranceType insuranceType, @RequestParam Packet packet) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(insuranceType, packet)));
    }
}
