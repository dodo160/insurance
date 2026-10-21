package com.insurance.temporal.api;

import com.insurance.common.exception.ErrorResponse;
import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.mapper.TemporalEntityMapper;
import com.insurance.temporal.service.TemporalEntityService;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/temporalEntity")
@Tag(name = "TemporalEntity", description = "TemporalEntity management operations")
public class TemporalEntityRestController {

    private TemporalEntityService temporalEntityService;

    private TemporalEntityMapper temporalEntityMapper;

    public TemporalEntityRestController(TemporalEntityService temporalEntityService, TemporalEntityMapper temporalEntityMapper) {
        this.temporalEntityService = temporalEntityService;
        this.temporalEntityMapper = temporalEntityMapper;
    }

    @Operation(summary = "Get temporal entity by ID", description = "Returns an temporal entity contract by its identifier")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Temporal entity found"),
            @ApiResponse(responseCode = "404", description = "Temporal entity doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TemporalEntityDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(temporalEntityMapper.toDto(temporalEntityService.findById(id)));
    }

    @Operation(summary = "Get all temporal entities", description = "Returns all temporal entities")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Temporal entities found"),
            @ApiResponse(responseCode = "404", description = "Temporal entities not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TemporalEntityDTO>> getAllTemporalEntities() {
        return ResponseEntity.ok(temporalEntityService.findAll().stream().map(temporalEntityMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Create temporal entity", description = "Creates a new temporal entity and returns the created temporal entity.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Temporal entity created"),
            @ApiResponse(responseCode = "404", description = "Tariff doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated. | Invalid XML. | Invalid JSON",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Invalid XML schema. | Invalid XML marshalling | Invalid JSON serialization.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TemporalEntityDTO> addTemporalEntity(@Valid @RequestBody final TemporalEntityDTO temporalEntityDTO) {
        final TemporalEntityDTO body = temporalEntityMapper.toDto(temporalEntityService.add(temporalEntityMapper.fromDto(temporalEntityDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Operation(summary = "Delete temporal entity by id", description = "Deletes an exist temporal entity.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Temporal entity deleted"),
            @ApiResponse(responseCode = "404", description = "Temporal entity doesn't exist",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemporalEntity(@PathVariable("id") final Long id) {
        temporalEntityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create entity form temporal entity", description = "Creates a new entity form temporal entity.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Temporal entity created"),
            @ApiResponse(responseCode = "400", description = "Invalid XML. | Invalid JSON",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Invalid XML schema. | Invalid XML marshalling | Invalid JSON serialization.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/createEntityFromTemporal/{id}")
    public ResponseEntity<Void> createEntityFromTemporal(@PathVariable("id") final Long id) {
        temporalEntityService.createEntityFromTemporal(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
