package com.insurance.tariff.api;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.tariff.service.TariffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<TariffDTO>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.findAll().stream().map(tariffMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<TariffDTO> addTariff(@RequestBody final TariffDTO tariffDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffMapper.toDto(tariffService.add(tariffMapper.fromDto(tariffDTO))));
    }

    @PutMapping()
    public ResponseEntity<TariffDTO> updateTariff(@RequestBody final TariffDTO tariffDTO) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.update(tariffMapper.fromDto(tariffDTO))));
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
