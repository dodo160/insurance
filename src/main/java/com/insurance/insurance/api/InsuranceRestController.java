package com.insurance.insurance.api;

import com.insurance.common.exception.RequestMismatchException;
import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.insurance.service.InsuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(insuranceMapper.toDto(insuranceService.findById(id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.findAll().stream().map(insuranceMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> addInsurance(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceMapper.toDto(insuranceService.add(insuranceMapper.fromDto(insuranceDTO))));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> updateInsurance(@PathVariable("id") final Long id, @Valid @RequestBody final InsuranceDTO insuranceDTO) {
        if (Objects.nonNull(insuranceDTO.getId()) && !id.equals(insuranceDTO.getId())) {
            throw new RequestMismatchException(
                    "Insurance ID in path does not match user ID in request body");
        }
        return ResponseEntity.ok(insuranceMapper.toDto(insuranceService.update(id, insuranceMapper.fromDto(insuranceDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable("id") final Long id) {
        insuranceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/softDelete/{id}")
    public ResponseEntity<Void> softDeleteInsurance(@PathVariable("id") final Long id) {
        insuranceService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BigDecimal> calculateInsurance(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceService.calculateInsurance(insuranceMapper.fromDto(insuranceDTO)));
    }
}
