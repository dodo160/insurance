package com.insurance.rest;

import com.insurance.mapper.CycleAvoidingMappingContext;
import com.insurance.mapper.InsuranceMapper;
import com.insurance.model.Insurance;
import com.insurance.modeldto.InsuranceDTO;
import com.insurance.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class InsuranceRestController {

	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private InsuranceMapper insuranceMapper;

	@GetMapping("/insurance/{id}")
	public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable("id") final Long id) {
		return new ResponseEntity<>(insuranceMapper.insuranceToInsuranceDTO(insuranceService.findById(id), new CycleAvoidingMappingContext()), HttpStatus.OK);
	}

	@GetMapping("/insurances")
	public ResponseEntity<Set<InsuranceDTO>> getAllInsurances() {
		return new ResponseEntity<>(insuranceService.findAll().stream().map(x->insuranceMapper.insuranceToInsuranceDTO(x, new CycleAvoidingMappingContext())).collect(Collectors.toSet()), HttpStatus.OK);
	}

	@RequestMapping(value = "/insurance/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addInsuracnce(@RequestBody final InsuranceDTO insuranceDTO) throws ValidationException {
		insuranceService.add(insuranceMapper.insuranceDTOtoInsurance(insuranceDTO, new CycleAvoidingMappingContext()));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/insurance/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InsuranceDTO> updateInsurance(@RequestBody final InsuranceDTO insuranceDTO) throws ValidationException {
		return new ResponseEntity<>(insuranceMapper.insuranceToInsuranceDTO(insuranceService.update(insuranceMapper.insuranceDTOtoInsurance(insuranceDTO, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext()), HttpStatus.OK);
	}

	@DeleteMapping("/insurance/delete/{id}")
	public ResponseEntity<Void> deleteInsuracnce(@PathVariable("id") final Long id) {
		insuranceService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/insurance/softDelete/{id}")
	public ResponseEntity<Void> softDeleteInsuracnce(@PathVariable("id") final Long id) {
		insuranceService.softDeleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/insurance/calculate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BigDecimal> calculateInsurance(@RequestBody final InsuranceDTO insuranceDTO) throws ValidationException {
		return new ResponseEntity<>(insuranceService.calculateInsurance(insuranceMapper.insuranceDTOtoInsurance(insuranceDTO, new CycleAvoidingMappingContext())), HttpStatus.OK);
	}
}
