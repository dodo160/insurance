package com.insurance.insurance.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.model.Reinsurance;
import com.insurance.insurance.repository.InsuranceRepository;
import com.insurance.insurance.service.calculator.InsurancePriceCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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
    public List<Insurance> findAll() {
        final List<Insurance> insurances = new ArrayList<>();
        insuranceRepository.findAll().forEach(insurances::add);
        if (insurances.isEmpty()) {
            throw new NotFoundException("No insurances found");
        }
        return insurances;
    }

    @Override
    public Insurance findById(final Long id) {
        return insuranceRepository.findById(id).orElseThrow(() -> new NotFoundException("No insurance found"));
    }

    @Override
    public Insurance add(final Insurance insurance) {
        insurance.updateReinsurances();
        insurance.setPrice(calculateInsurance(insurance));
        return insuranceRepository.save(insurance);
    }

    @Override
    public Insurance update(final Insurance insurance) {
        final Insurance ins = findById(insurance.getId());
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
        final Insurance insurance = insuranceRepository.findById(id).orElseThrow(() -> new NotFoundException("Insurance entity doesn't exist"));
        insurance.setDeletedDate(LocalDateTime.now());
        insuranceRepository.save(insurance);
    }

    @Override
    public BigDecimal calculateInsurance(final Insurance insurance) {
        return insurancePriceCalculator.calculateInsurance(insurance);
    }

    private void refreshReinsurences(final Insurance insurance, final Insurance ins) {
        if (Objects.nonNull(insurance.getReinsurances()) && Objects.nonNull(ins.getReinsurances()) &&
                !insurance.getReinsurances().containsAll(ins.getReinsurances()) && !ins.getReinsurances().containsAll(insurance.getReinsurances())) {
            if (insurance.getReinsurances().size() < ins.getReinsurances().size()) {
                final Set<Reinsurance> reinsurancesToBeDeleted = new HashSet<>();
                for (Reinsurance r : ins.getReinsurances()) {
                    if (insurance.getReinsurances().stream().noneMatch(x -> r.getId().equals(x.getId()))) {
                        reinsurancesToBeDeleted.add(r);
                    }
                }
                if (!reinsurancesToBeDeleted.isEmpty()) {
                    ins.getReinsurances().removeAll(reinsurancesToBeDeleted);
                }
            } else if (insurance.getReinsurances().size() > ins.getReinsurances().size()) {
                ins.getReinsurances().addAll(insurance.getReinsurances().stream().filter(x -> Objects.isNull(x.getId())).collect(Collectors.toSet()));
            }
        }
    }
}
