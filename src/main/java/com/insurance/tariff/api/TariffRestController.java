package com.insurance.tariff.api;

import com.insurance.common.exception.ErrorResponse;
import com.insurance.common.exception.RequestMismatchException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.tariff.service.TariffService;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tariff")
@Tag(name = "Tariff", description = "Tariff management operations")
public class TariffRestController {

    private final TariffService tariffService;

    private final TariffMapper tariffMapper;

    public TariffRestController(TariffService tariffService, TariffMapper tariffMapper) {
        this.tariffService = tariffService;
        this.tariffMapper = tariffMapper;
    }

    @Operation(summary = "Get tariff by ID", description = "Returns an tariff by its identifier")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tariff found"),
            @ApiResponse(responseCode = "404", description = "Tariff not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.findById(id)));
    }

    @Operation(summary = "Get all tariffs", description = "Returns all tariffs")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tariffs found"),
            @ApiResponse(responseCode = "404", description = "No tariffs found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TariffDTO>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.findAll().stream().map(tariffMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Create tariff", description = "Creates a new tariff and returns the created tariff.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Tariff created"),
            @ApiResponse(responseCode = "409", description = "Active tariff already exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> addTariff(@RequestBody final TariffDTO tariffDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffMapper.toDto(tariffService.add(tariffMapper.fromDto(tariffDTO))));
    }

    @Operation(summary = "Update tariff by id", description = "Updates an existing tariff.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tariff updated"),
            @ApiResponse(responseCode = "404", description = "Tariff not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> updateTariff(@PathVariable final Long id, @RequestBody final TariffDTO tariffDTO) {
        if (Objects.nonNull(tariffDTO.getId()) && !id.equals(tariffDTO.getId())) {
            throw new RequestMismatchException(
                    "Tariff ID in path does not match user ID in request body");
        }
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.update(id, tariffMapper.fromDto(tariffDTO))));
    }

    @Operation(summary = "Delete tariff by id", description = "Deletes an exist tariff.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Tariff deleted"),
            @ApiResponse(responseCode = "404", description = "Tariff not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTariff(@PathVariable("id") final Long id) {
        tariffService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get tariff by insurance type and packet", description = "Get tariff by insurance type and packet")
    @GetMapping(value = "/tariffByInsuranceTypeAndPacket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TariffDTO> getTariffByInsuranceTypeAndPacket(@RequestParam InsuranceType insuranceType, @RequestParam Packet packet) {
        return ResponseEntity.ok(tariffMapper.toDto(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(insuranceType, packet)));
    }
}
