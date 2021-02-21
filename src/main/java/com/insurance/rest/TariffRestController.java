package com.insurance.rest;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.mapper.CycleAvoidingMappingContext;
import com.insurance.mapper.TariffMapper;
import com.insurance.modeldto.TariffDTO;
import com.insurance.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class TariffRestController {

	@Autowired
	private TariffService tariffService;

	@Autowired
	private TariffMapper tariffMapper;

	@GetMapping("/tariff/{id}")
	public ResponseEntity<TariffDTO> getById(@PathVariable final Long id) {
		return new ResponseEntity<>(tariffMapper.tariffToTariffDTO(tariffService.findById(id), new CycleAvoidingMappingContext()), HttpStatus.OK);
	}

	@GetMapping("/tariffs")
	public ResponseEntity<Set<TariffDTO>> getAllTariffs() {
		return new ResponseEntity<>(tariffService.findAll().stream().map(x->tariffMapper.tariffToTariffDTO(x, new CycleAvoidingMappingContext())).collect(Collectors.toSet()), HttpStatus.OK);
	}

	@PostMapping("/tariff/add")
	public ResponseEntity<Void> addTariff(@RequestBody final TariffDTO tariffDTO) throws ValidationException {
		tariffService.add(tariffMapper.tariffDTOtoTariff(tariffDTO, new CycleAvoidingMappingContext()));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/tariff/delete/{id}")
	public ResponseEntity<Void> deleteTariff(@PathVariable("id") final Long id) {
		tariffService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tariff/softDelete/{id}")
	public ResponseEntity<Void> softDeleteTariff(@PathVariable("id") final Long id) throws ValidationException {
		tariffService.softDeleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/tariffByInsuranceTypeAndPacket")
	public ResponseEntity<TariffDTO> getTariffByInsuranceTypeAndPacket(@RequestParam InsuranceType insuranceType, @RequestParam Packet packet){
		return new ResponseEntity<>(tariffMapper.tariffToTariffDTO(tariffService.getTariffInsuranceTypeAndPacket(insuranceType, packet), new CycleAvoidingMappingContext()), HttpStatus.OK);
	}
}
