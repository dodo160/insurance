package com.insurance.insurance.api;

import com.insurance.common.exception.ErrorResponse;
import com.insurance.common.exception.RequestMismatchException;
import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.insurance.service.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Insurance", description = "Insurance management operations")
public class InsuranceRestController {

    private final InsuranceService insuranceService;

    private final InsuranceMapper insuranceMapper;

    public InsuranceRestController(InsuranceService insuranceService, InsuranceMapper insuranceMapper) {
        this.insuranceService = insuranceService;
        this.insuranceMapper = insuranceMapper;
    }

    @Operation(summary = "Get insurance by ID", description = "Returns an insurance contract by its identifier")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Insurance found"),
                    @ApiResponse(responseCode = "404", description = "Insurance not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(insuranceMapper.toDto(insuranceService.findById(id)));
    }

    @Operation(summary = "Get all insurances", description = "Returns all insurances")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Insurance found"),
            @ApiResponse(responseCode = "404", description = "Insurance not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.findAll().stream().map(insuranceMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Create insurance", description = "Creates a new insurance and returns the created insurance.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Insurance created"),
            @ApiResponse(responseCode = "404", description = "Tariff doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> addInsurance(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceMapper.toDto(insuranceService.add(insuranceMapper.fromDto(insuranceDTO))));
    }

    @Operation(summary = "Update insurance by id", description = "Updates an existing insurance.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Insurance updated"),
            @ApiResponse(responseCode = "404", description = "Insurance or tariff not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> updateInsurance(@PathVariable("id") final Long id, @Valid @RequestBody final InsuranceDTO insuranceDTO) {
        if (Objects.nonNull(insuranceDTO.getId()) && !id.equals(insuranceDTO.getId())) {
            throw new RequestMismatchException(
                    "Insurance ID in path does not match user ID in request body");
        }
        return ResponseEntity.ok(insuranceMapper.toDto(insuranceService.update(id, insuranceMapper.fromDto(insuranceDTO))));
    }

    @Operation(summary = "Delete insurance by id", description = "Deletes an exist insurance.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Insurance deleted"),
            @ApiResponse(responseCode = "404", description = "Insurance doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable("id") final Long id) {
        insuranceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Soft delete insurance by id", description = "Mark insurance as deleted.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Insurance deleted"),
            @ApiResponse(responseCode = "404", description = "Insurance doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/softDelete/{id}")
    public ResponseEntity<Void> softDeleteInsurance(@PathVariable("id") final Long id) {
        insuranceService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Calculate insurance price", description = "Calculates insurance price based on tariff and selected reinsurance options")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Insurance calculated"),
            @ApiResponse(responseCode = "404", description = "Tariff doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BigDecimal> calculateInsurance(@Valid @RequestBody final InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceService.calculateInsurance(insuranceMapper.fromDto(insuranceDTO)));
    }
}
