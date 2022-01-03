package com.insurance.service;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.ReinsuranceType;
import com.insurance.model.Insurance;
import com.insurance.model.Reinsurance;
import com.insurance.model.Tariff;
import com.insurance.repository.InsuranceRepository;
import com.insurance.validators.InsuranceValidator;
import com.insurance.validators.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class InsuranceServiceImpl implements InsuranceService {

	private InsuranceRepository insuranceRepository;
	private ConfigProperties configProperties;
	private ValidatorFactory validatorFactory;
	private TariffService tariffService;

	public InsuranceServiceImpl(final InsuranceRepository insuranceRepository, final ConfigProperties configProperties,
								final ValidatorFactory validatorFactory, final TariffService tariffService) {
		this.insuranceRepository=insuranceRepository;
		this.configProperties=configProperties;
		this.validatorFactory=validatorFactory;
		this.tariffService=tariffService;
	}

	public Class getEntityServiceClass(){
		return Insurance.class;
	}

	@Override
	public Set<Insurance> findAll() {
		final Set<Insurance> insurances = new HashSet<>();
		insuranceRepository.findAll().forEach(insurances::add);
		return insurances;
	}

	@Override
	public Insurance findById(final Long id) {
		return insuranceRepository.findById(id).orElse(null);
	}

	@Override
	public Insurance add(final Insurance insurance) throws ValidationException {
		insurance.updateReinsurances();
		insurance.setPrice(calculateInsurance(insurance));
		return insuranceRepository.save(insurance);
	}

	@Override
	public Insurance update(final Insurance insurance) throws ValidationException {
		final Insurance ins = insuranceRepository.findById(insurance.getId()).orElse(null);
		if (Objects.isNull(ins)) {
			throw new ValidationException("Insurance entity doesn't exist");
		}
		ins.setTariff(insurance.getTariff());
		ins.setUser(insurance.getUser());
		ins.setStartDate(insurance.getStartDate());
		ins.setEndDate(insurance.getEndDate());
		ins.setPerson(insurance.getPerson());
		refreshReinsurences(insurance, ins);
		ins.setPrice(calculateInsurance(ins));
		ins.updateReinsurances();
		return insuranceRepository.save(ins);
	}

	@Override
	public void deleteById(final Long id) {
		insuranceRepository.deleteById(id);
	}

	@Override
	public void softDeleteById(Long id) {
		final Insurance insurance = insuranceRepository.findById(id).orElse(null);
		if(Objects.nonNull(insurance)) {
			insurance.setDeletedDate(LocalDateTime.now());
			insuranceRepository.save(insurance);
		}
	}

	@Override
	public BigDecimal calculateInsurance(final Insurance insurance) throws ValidationException {
		Assert.notNull(insurance, "Missing Insurance");
		Assert.notNull(insurance.getTariff(), "Missing Tariff");
		final InsuranceValidator validator = validatorFactory.getValidator(insurance.getTariff().getInsuranceType());
		validator.validate(insurance);
		final Tariff tariffDB = tariffService.getTariffInsuranceTypeAndPacket(insurance.getTariff().getInsuranceType(), insurance.getTariff().getPacket());
		if(Objects.isNull(tariffDB)){
			throw new ValidationException("Tarif doesn't exist");
		}
		final BigDecimal tariffPrice = tariffDB.getPrice();

		BigDecimal reinsurance = BigDecimal.ONE;
		if (!insurance.getReinsurances().isEmpty()) {
			for (Reinsurance r : insurance.getReinsurances()) {
				reinsurance = reinsurance
						.multiply(getReinsurance(insurance.getTariff().getInsuranceType(), r.getReinsuranceType()));
			}
		}

		if (InsuranceType.DAY == insurance.getTariff().getInsuranceType()) {
			return calculateInsuranceDaily(insurance, reinsurance, tariffPrice);
		} else {
			return calcuteInsuranceYear(insurance, reinsurance, tariffPrice);
		}
	}

	private BigDecimal calculateInsuranceDaily(final Insurance insurance, final BigDecimal reinsurance,
											   final BigDecimal tariffPrice) {
		final long days = ChronoUnit.DAYS.between(insurance.getStartDate(), insurance.getEndDate()) + 1;
		final BigDecimal result = BigDecimal.valueOf(days * insurance.getPerson()).multiply(tariffPrice);

		return Objects.isNull(reinsurance) ? result.setScale(2, RoundingMode.HALF_UP) : result.multiply(reinsurance).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal calcuteInsuranceYear(final Insurance insurance, final BigDecimal reinsurance,
											final BigDecimal tariffPrice) {
	    insurance.setEndDate(insurance.getStartDate().plusYears(1));
		final BigDecimal result = BigDecimal.valueOf(insurance.getPerson()).multiply(tariffPrice);
		return Objects.isNull(reinsurance) ? result.setScale(2, RoundingMode.HALF_UP) : result.multiply(reinsurance).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal getReinsurance(final InsuranceType insuranceType, final ReinsuranceType reinsuranceType) {
		Assert.notNull(insuranceType, "Missing InsuranceType");
		Assert.notNull(reinsuranceType, "Missing ReinsuranceType");
		final String confKey = ConfigPropertiesImpl.PREFIX + insuranceType.name() + "." + reinsuranceType.name();
		final String value = configProperties.getConfigValue(confKey);
		return Objects.isNull(value) ? BigDecimal.ONE : new BigDecimal(value);
	}

	private void refreshReinsurences(final Insurance insurance, final Insurance ins){
		if(Objects.nonNull(insurance.getReinsurances()) && Objects.nonNull(ins.getReinsurances()) &&
				!insurance.getReinsurances().containsAll(ins.getReinsurances()) && !ins.getReinsurances().containsAll(insurance.getReinsurances())) {
			if (insurance.getReinsurances().size() < ins.getReinsurances().size()) {
				final Set<Reinsurance> reinsurancesToBeDeleted = new HashSet<>();
				for (Reinsurance r : ins.getReinsurances()) {
					if (insurance.getReinsurances().stream().noneMatch(x -> r.getId().equals(x.getId()))) {
						reinsurancesToBeDeleted.add(r);
					}
				}
				if(!reinsurancesToBeDeleted.isEmpty()) {
					ins.getReinsurances().removeAll(reinsurancesToBeDeleted);
				}
			} else if (insurance.getReinsurances().size() > ins.getReinsurances().size()) {
				ins.getReinsurances().addAll(insurance.getReinsurances().stream().filter(x -> Objects.isNull(x.getId())).collect(Collectors.toSet()));
			}
		}
	}
}
