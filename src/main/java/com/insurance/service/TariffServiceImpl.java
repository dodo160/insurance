package com.insurance.service;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.model.Tariff;
import com.insurance.repository.TariffRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class TariffServiceImpl implements TariffService {

	private TariffRepository tariffRepository;

	public TariffServiceImpl(final TariffRepository tariffRepository) {
		this.tariffRepository=tariffRepository;
	}

	@Override
	public Set<Tariff> findAll() {
		final Set<Tariff> tariffs = new HashSet<>();
		tariffRepository.findAll().forEach(tariffs::add);
		return tariffs;
	}

	@Override
	public Tariff findById(final Long id) {
		return tariffRepository.findById(id).orElse(null);
	}

	@Override
	public Tariff add(final Tariff tariff) {
		return tariffRepository.save(tariff);
	}

	@Override
	public Tariff update(final Tariff tariff) throws ValidationException {
		final Tariff tar = findById(tariff.getId());
		if (Objects.isNull(tar)) {
			throw new ValidationException("Tariff entity doesn't exist");
		}
		tar.setInsuranceType(tariff.getInsuranceType());
		tar.setPacket(tariff.getPacket());
		tar.setPrice(tariff.getPrice());
		return tariffRepository.save(tar);
	}

	@Override
	public void deleteById(final Long id) {
		tariffRepository.deleteById(id);
	}

	@Override
	public void softDeleteById(final Long id) {
		final Tariff tariff = tariffRepository.findById(id).orElse(null);
		if(Objects.nonNull(tariff)) {
			tariff.setDeletedDate(LocalDateTime.now());
			tariffRepository.save(tariff);
		}
	}

	@Override
	public Tariff getTariffInsuranceTypeAndPacket(final InsuranceType insType, final Packet packet){
		return tariffRepository.getTariffByInsuranceTypeAndPacket(insType, packet);
	}
}
