package com.insurance.rest;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.mapper.TariffMapper;
import com.insurance.modeldto.TariffDTO;
import com.insurance.service.TariffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
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

    @GetMapping("/{id}")
    public ResponseEntity<TariffDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<Set<TariffDTO>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.findAll().stream().map(tariffMapper::toDto).collect(Collectors.toSet()));
    }

    @PostMapping()
    public ResponseEntity<TariffDTO> addTariff(@RequestBody final TariffDTO tariffDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffMapper.toDto(tariffService.add(tariffMapper.fromDto(tariffDTO))));
    }

    @PutMapping()
    public ResponseEntity<TariffDTO> updateTariff(@RequestBody final TariffDTO tariffDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffMapper.toDto(tariffService.update(tariffMapper.fromDto(tariffDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTariff(@PathVariable("id") final Long id) {
        tariffService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tariffByInsuranceTypeAndPacket")
    public ResponseEntity<TariffDTO> getTariffByInsuranceTypeAndPacket(@RequestParam InsuranceType insuranceType, @RequestParam Packet packet) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(insuranceType, packet)));
    }
}
