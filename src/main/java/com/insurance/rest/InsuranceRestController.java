package com.insurance.rest;

import com.insurance.mapper.InsuranceMapper;
import com.insurance.modeldto.InsuranceDTO;
import com.insurance.service.InsuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/insurance")
public class InsuranceRestController {

    private final InsuranceService insuranceService;

    private final InsuranceMapper insuranceMapper;

    public InsuranceRestController(InsuranceService insuranceService, InsuranceMapper insuranceMapper) {
        this.insuranceService = insuranceService;
        this.insuranceMapper = insuranceMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(insuranceMapper.toDto(insuranceService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<Set<InsuranceDTO>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.findAll().stream().map(insuranceMapper::toDto).collect(Collectors.toSet()));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> addInsuracnce(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceMapper.toDto(insuranceService.add(insuranceMapper.fromDto(insuranceDTO))));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> updateInsurance(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceMapper.toDto(insuranceService.update(insuranceMapper.fromDto(insuranceDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsuracnce(@PathVariable("id") final Long id) {
        insuranceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/softDelete/{id}")
    public ResponseEntity<Void> softDeleteInsuracnce(@PathVariable("id") final Long id) {
        insuranceService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BigDecimal> calculateInsurance(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceService.calculateInsurance(insuranceMapper.fromDto(insuranceDTO)));
    }
}
