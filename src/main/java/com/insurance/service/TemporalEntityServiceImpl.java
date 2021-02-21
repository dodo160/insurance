package com.insurance.service;

import com.google.common.collect.ImmutableSet;
import com.insurance.model.Insurance;
import com.insurance.model.Tariff;
import com.insurance.model.TemporalEntity;
import com.insurance.repository.TemporalEntityRepository;
import com.insurance.xml.XmlUtils;
import com.insurance.xml.xmlvalidator.XmlValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.xml.bind.ValidationException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TemporalEntityServiceImpl implements TemporalEntityService {

    private TemporalEntityRepository temporalEntityRepository;

    private XmlValidator xmlValidator;

    private final Map<Class, BasicService> services = new HashMap<>();

    public TemporalEntityServiceImpl(final TemporalEntityRepository temporalEntityRepository, final XmlValidator xmlValidator, final Set<BasicService> services) {
        this.temporalEntityRepository = temporalEntityRepository;
        this.xmlValidator = xmlValidator;
        this.services.putAll(services.stream().collect(Collectors.toMap(BasicService::getEntityServiceClass, Function.identity())));
    }

    public Class getEntityServiceClass() {
        return TemporalEntity.class;
    }

    @Override
    public Set<TemporalEntity> findAll() {
        final Set<TemporalEntity> temporalEntities = new HashSet<>();
        temporalEntityRepository.findAll().forEach(temporalEntities::add);
        return temporalEntities;
    }

    @Override
    public TemporalEntity findById(final Long id) {
        return temporalEntityRepository.findById(id).orElse(null);
    }

    @Override
    public TemporalEntity add(final TemporalEntity entity) throws ValidationException {
        Objects.nonNull(entity);
        Objects.nonNull(entity.getEntityClass());
        Objects.nonNull(entity.getUser());
        Objects.nonNull(entity.getMediaType());
        Objects.nonNull(entity.getEntity());
        xmlValidator.validateXml(entity.getEntity(), entity.getEntityClass());
        return temporalEntityRepository.save(entity);
    }

    @Override
    public TemporalEntity update(final TemporalEntity entity) throws ValidationException {
        throw new ValidationException("Not supported action.");
    }

    @Override
    public void deleteById(final Long id) {
        temporalEntityRepository.deleteById(id);
    }

    @Override
    public void softDeleteById(final Long id) throws ValidationException {
        throw new ValidationException("Not supported action.");
    }

    @Override
    public void createEntityFromTemporal(final Long id) throws ValidationException {
        final TemporalEntity temporalEntity = findById(id);
        Assert.notNull(temporalEntity, "Temporal entity not found.");
        try {
            if (ImmutableSet.of(Insurance.class.getSimpleName(), Tariff.class.getSimpleName()).contains(temporalEntity.getEntityClass())) {
                final Class entityClass = Class.forName("com.insurance.model." + temporalEntity.getEntityClass());
                xmlValidator.validateXml(temporalEntity.getEntity(), temporalEntity.getEntityClass());
                final Object entity = XmlUtils.fromXml(entityClass, temporalEntity.getEntity());
                final BasicService basicService = services.getOrDefault(entityClass, null);

                if (Objects.nonNull(entity) && Objects.nonNull(basicService)) {
                    basicService.add(entity);
                    temporalEntityRepository.deleteById(temporalEntity.getId());
                }
            }else{
                throw new ValidationException("Not supproted entity.");
            }
        } catch (ClassNotFoundException e) {
            throw new ValidationException(e);
        }
    }
}
