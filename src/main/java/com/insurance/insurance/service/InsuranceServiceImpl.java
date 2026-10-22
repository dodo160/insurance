package com.insurance.insurance.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.insurance.enums.ReinsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.model.Reinsurance;
import com.insurance.insurance.repository.InsuranceRepository;
import com.insurance.insurance.service.calculator.InsurancePriceCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final InsurancePriceCalculator insurancePriceCalculator;

    public InsuranceServiceImpl(final InsuranceRepository insuranceRepository, final InsurancePriceCalculator insurancePriceCalculator) {
        this.insuranceRepository = insuranceRepository;
        this.insurancePriceCalculator = insurancePriceCalculator;
    }

    public Class getEntityServiceClass() {
        return Insurance.class;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> findAll() {
        final List<Insurance> insurances = new ArrayList<>();
        insuranceRepository.findAll().forEach(insurances::add);
        if (insurances.isEmpty()) {
            throw new NotFoundException("No insurances found");
        }
        return insurances;
    }

    @Override
    @Transactional(readOnly = true)
    public Insurance findById(final Long id) {
        return insuranceRepository.findById(id).orElseThrow(() -> new NotFoundException("Insurance not found"));
    }

    @Override
    @Transactional
    public Insurance add(final Insurance insurance) {
        insurance.updateReinsurances();
        insurance.setPrice(calculateInsurance(insurance));
        return insuranceRepository.save(insurance);
    }

    @Override
    @Transactional
    public Insurance update(final Long id, final Insurance insurance) {
        final Insurance ins = findById(id);
        ins.setTariff(insurance.getTariff());
        ins.setUser(insurance.getUser());
        ins.setStartDate(insurance.getStartDate());
        ins.setEndDate(insurance.getEndDate());
        ins.setPerson(insurance.getPerson());
        refreshReinsurences(insurance, ins);
        ins.setPrice(calculateInsurance(ins));
        return ins;
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        findById(id);
        insuranceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        final Insurance insurance = findById(id);
        insurance.setDeletedDate(LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateInsurance(final Insurance insurance) {
        return insurancePriceCalculator.calculateInsurance(insurance);
    }

    private void refreshReinsurences(final Insurance insurance, final Insurance insuranceDB) {
        final Set<ReinsuranceType> inputReinsuranceTypes = insurance.getReinsurances().stream()
                .map(Reinsurance::getReinsuranceType)
                .collect(Collectors.toSet());
        insuranceDB.getReinsurances()
                .removeIf(r -> !inputReinsuranceTypes.contains(r.getReinsuranceType()));

        insurance.getReinsurances().stream()
                .filter(x -> insuranceDB.getReinsurances().stream()
                        .noneMatch(y -> y.getReinsuranceType() == x.getReinsuranceType()))
                .forEach(x -> {
            final Reinsurance reinsurance = new Reinsurance();
            reinsurance.setReinsuranceType(x.getReinsuranceType());
            insuranceDB.addReinsurance(reinsurance);
        });
    }
}
