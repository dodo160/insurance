package com.insurance.temporal.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.common.json.JsonMarshaller;
import com.insurance.common.service.BasicService;
import com.insurance.common.xml.XmlMarshaller;
import com.insurance.common.xml.xmlvalidator.XmlValidator;
import com.insurance.insurance.model.Insurance;
import com.insurance.tariff.client.TariffGateway;
import com.insurance.tariff.model.TariffEmbedded;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.temporal.repository.TemporalEntityRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TemporalEntityServiceImpl implements TemporalEntityService {

    private final TemporalEntityRepository temporalEntityRepository;

    private final XmlValidator xmlValidator;

    private final XmlMarshaller xmlMarshaller;

    private final JsonMarshaller jsonMarshaller;

    private final Map<Class, BasicService> services = new HashMap<>();

    private final TariffGateway tariffGateway;

    public TemporalEntityServiceImpl(final TemporalEntityRepository temporalEntityRepository, final XmlValidator xmlValidator,
                                     final XmlMarshaller xmlMarshaller, final JsonMarshaller jsonMarshaller, final Set<BasicService> services,
                                     final TariffGateway tariffGateway) {
        this.temporalEntityRepository = temporalEntityRepository;
        this.xmlValidator = xmlValidator;
        this.services.putAll(services.stream().collect(Collectors.toMap(BasicService::getEntityServiceClass, Function.identity())));
        this.xmlMarshaller = xmlMarshaller;
        this.jsonMarshaller = jsonMarshaller;
        this.tariffGateway = tariffGateway;
    }

    public Class getEntityServiceClass() {
        return TemporalEntity.class;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemporalEntity> findAll() {
        final List<TemporalEntity> temporalEntities = new ArrayList<>();
        temporalEntityRepository.findAll().forEach(temporalEntities::add);
        if (temporalEntities.isEmpty()) {
            throw new NotFoundException("Temporal entities not found");
        }
        return temporalEntities;
    }

    @Override
    @Transactional(readOnly = true)
    public TemporalEntity findById(final Long id) {
        return temporalEntityRepository.findById(id).orElseThrow(()-> new NotFoundException("Temporal entity doesn't exist"));
    }

    @Override
    @Transactional
    public TemporalEntity add(final TemporalEntity entity) {
        switch (entity.getMediaType()) {
            case MediaType.APPLICATION_XML_VALUE:
                xmlValidator.validateXml(entity.getEntity(), entity.getEntityClass());
                break;
            case MediaType.APPLICATION_JSON_VALUE:
                jsonMarshaller.isValidJson(entity.getEntity());
                break;
            default:
                throw new IllegalArgumentException("Not supported Media Type");
        }
        return temporalEntityRepository.save(entity);
    }

    @Override
    public TemporalEntity update(final Long id, final TemporalEntity entity) {
        throw new UnsupportedOperationException("Not supported action.");
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        findById(id);
        temporalEntityRepository.deleteById(id);
    }

    @Override
    public void softDeleteById(final Long id) {
        throw new UnsupportedOperationException("Not supported action.");
    }

    @Override
    @Transactional
    public void createEntityFromTemporal(final Long id) {
        final TemporalEntity temporalEntity = findById(id);
        if ("Tariff".equals(temporalEntity.getEntityClass())) {
            tariffGateway.add(readEntity(temporalEntity, TariffEmbedded.class));
        } else if (Insurance.class.getSimpleName().equals(temporalEntity.getEntityClass())) {
            services.get(Insurance.class).add(readEntity(temporalEntity, Insurance.class));
        } else {
            throw new IllegalArgumentException("Not supported entity.");
        }

        temporalEntityRepository.deleteById(temporalEntity.getId());
    }

    private <T> T readEntity(final TemporalEntity temporalEntity, final Class<T> entityClass) {
        switch (temporalEntity.getMediaType()) {
            case MediaType.APPLICATION_XML_VALUE:
                xmlValidator.validateXml(temporalEntity.getEntity(), temporalEntity.getEntityClass());
                return xmlMarshaller.fromXml(entityClass, temporalEntity.getEntity());
            case MediaType.APPLICATION_JSON_VALUE:
                return jsonMarshaller.fromJson(entityClass, temporalEntity.getEntity());
            default:
                throw new IllegalArgumentException("Not supported Media Type " + temporalEntity.getMediaType());
        }
    }
}
