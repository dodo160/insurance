package com.insurance.rest;

import com.insurance.mapper.TemporalEntityMapper;
import com.insurance.modeldto.TemporalEntityDTO;
import com.insurance.service.TemporalEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/temporalEntity")
public class TemporalEntityRestController {

    private TemporalEntityService temporalEntityService;

    private TemporalEntityMapper temporalEntityMapper;

    public TemporalEntityRestController(TemporalEntityService temporalEntityService, TemporalEntityMapper temporalEntityMapper) {
        this.temporalEntityService = temporalEntityService;
        this.temporalEntityMapper = temporalEntityMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemporalEntityDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(temporalEntityMapper.toDto(temporalEntityService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<Set<TemporalEntityDTO>> getAllTemporalEntities() {
        return ResponseEntity.ok(temporalEntityService.findAll().stream().map(temporalEntityMapper::toDto).collect(Collectors.toSet()));
    }

    @PostMapping()
    public ResponseEntity<TemporalEntityDTO> addTemporalEntity(@RequestBody final TemporalEntityDTO temporalEntityDTO) {
        final TemporalEntityDTO body = temporalEntityMapper.toDto(temporalEntityService.add(temporalEntityMapper.fromDto(temporalEntityDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemporalEntity(@PathVariable("id") final Long id) {
        temporalEntityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/createEntityFromTemporal/{id}")
    public ResponseEntity<Void> createEntityFromTemporal(@PathVariable("id") final Long id) {
        temporalEntityService.createEntityFromTemporal(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
