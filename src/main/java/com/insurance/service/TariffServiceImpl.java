package com.insurance.service;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.exception.DuplicateException;
import com.insurance.exception.NotFoundException;
import com.insurance.model.Tariff;
import com.insurance.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TariffServiceImpl implements TariffService {

    private TariffRepository tariffRepository;

    public TariffServiceImpl(final TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Class getEntityServiceClass() {
        return Tariff.class;
    }

    @Override
    public List<Tariff> findAll() {
        final List<Tariff> tariffs = new ArrayList<>();
        tariffRepository.findAll().forEach(tariffs::add);
        if (tariffs.isEmpty()) {
            throw new NotFoundException("No tariffs found");
        }
        return tariffs;
    }

	@Override
	public Tariff findById(final Long id) {
		return tariffRepository.findById(id).orElseThrow(() -> new NotFoundException("Tariff not found"));
	}

    @Override
    public Tariff add(final Tariff tariff) {
        if (Objects.nonNull(tariffRepository.getTariffByInsuranceTypeAndPacketAndActiveTrue(tariff.getInsuranceType(), tariff.getPacket()))) {
            throw new DuplicateException("Active tariff already exist with type " + tariff.getInsuranceType() + " and packet " + tariff.getPacket());
        }
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff update(final Tariff tariff) {
        final Tariff tar = findById(tariff.getId());
        tar.setInsuranceType(tariff.getInsuranceType());
        tar.setPacket(tariff.getPacket());
        tar.setPrice(tariff.getPrice());
        tar.setActive(tariff.isActive());
        return tariffRepository.save(tar);
    }

    @Override
    public void deleteById(final Long id) {
        tariffRepository.deleteById(id);
    }

	@Override
	public void softDeleteById(final Long id) {
		final Tariff tariff = findById(id);
		tariff.setDeletedDate(LocalDateTime.now());
		tariffRepository.save(tariff);
	}

    @Override
    public Tariff getTariffByInsuranceTypeAndPacketAndActiveTrue(final InsuranceType insType, final Packet packet) {
        return tariffRepository.getTariffByInsuranceTypeAndPacketAndActiveTrue(insType, packet);
    }
}
