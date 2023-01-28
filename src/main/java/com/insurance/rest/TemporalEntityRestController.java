package com.insurance.rest;

import com.insurance.mapper.TemporalEntityMapper;
import com.insurance.modeldto.TemporalEntityDTO;
import com.insurance.service.TemporalEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class TemporalEntityRestController {

    @Autowired
    private TemporalEntityService temporalEntityService;

    @Autowired
    private TemporalEntityMapper temporalEntityMapper;

    @GetMapping("/temporalEntity/{id}")
    public ResponseEntity<TemporalEntityDTO> getById(@PathVariable final Long id) {
        return new ResponseEntity<>(temporalEntityMapper.temporalEntityToTemporalEntityDTO(temporalEntityService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/temporalEntities")
    public ResponseEntity<Set<TemporalEntityDTO>> getAllTemporalEntities() {
        return new ResponseEntity<>(temporalEntityService.findAll().stream().map(x-> temporalEntityMapper.temporalEntityToTemporalEntityDTO(x)).collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PostMapping("/temporalEntity/add")
    public ResponseEntity<Void> addTemporalEntity(@RequestBody final TemporalEntityDTO temporalEntityDTO) throws ValidationException {
        temporalEntityService.add(temporalEntityMapper.temporalEntityDTOtoTemporalEntity(temporalEntityDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/temporalEntity/delete/{id}")
    public ResponseEntity<Void> deleteTemporalEntity(@PathVariable("id") final Long id) {
        temporalEntityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/temporalEntity/{id}/createEntityFromTemporal")
    public ResponseEntity<Void> createEntityFromTemporal(@PathVariable("id") final Long id) throws ValidationException {
        temporalEntityService.createEntityFromTemporal(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
