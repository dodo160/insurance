package com.insurance.service;

import com.insurance.exception.NotFoundException;
import com.insurance.json.JsonMarshaller;
import com.insurance.model.BaseEntity;
import com.insurance.model.Insurance;
import com.insurance.model.Tariff;
import com.insurance.model.TemporalEntity;
import com.insurance.repository.TemporalEntityRepository;
import com.insurance.xml.XmlMarshaller;
import com.insurance.xml.xmlvalidator.XmlValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    private static final Map<String, Class<? extends BaseEntity>> SUPPORTED_ENTITIES =
            Map.of(Insurance.class.getSimpleName(), Insurance.class,
                    Tariff.class.getSimpleName(), Tariff.class);

    public TemporalEntityServiceImpl(final TemporalEntityRepository temporalEntityRepository, final XmlValidator xmlValidator,
                                     final XmlMarshaller xmlMarshaller, final JsonMarshaller jsonMarshaller, final Set<BasicService> services) {
        this.temporalEntityRepository = temporalEntityRepository;
        this.xmlValidator = xmlValidator;
        this.services.putAll(services.stream().collect(Collectors.toMap(BasicService::getEntityServiceClass, Function.identity())));
        this.xmlMarshaller = xmlMarshaller;
        this.jsonMarshaller = jsonMarshaller;
    }

    public Class getEntityServiceClass() {
        return TemporalEntity.class;
    }

    @Override
    public List<TemporalEntity> findAll() {
        final List<TemporalEntity> temporalEntities = new ArrayList<>();
        temporalEntityRepository.findAll().forEach(temporalEntities::add);
        return temporalEntities;
    }

    @Override
    public TemporalEntity findById(final Long id) {
        return temporalEntityRepository.findById(id).orElseThrow(()-> new NotFoundException("Temporal entity doesn't exist"));
    }

    @Override
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
    public TemporalEntity update(final TemporalEntity entity) {
        throw new UnsupportedOperationException("Not supported action.");
    }

    @Override
    public void deleteById(final Long id) {
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
        final Class entityClass = SUPPORTED_ENTITIES.get(temporalEntity.getEntityClass());
        if (Objects.isNull(entityClass)) {
            throw new IllegalArgumentException("Not supported entity.");
        }
        BaseEntity entity = null;

        switch (temporalEntity.getMediaType()) {
            case MediaType.APPLICATION_XML_VALUE:
                entity = createEntityFromTemporalXML(temporalEntity, entityClass);
                break;
            case MediaType.APPLICATION_JSON_VALUE:
                entity = createEntityFromTemporalJSON(temporalEntity, entityClass);
                break;
            default:
                throw new IllegalArgumentException("Not supported Media Type" + temporalEntity.getMediaType());
        }

        final BasicService basicService = services.getOrDefault(entityClass, null);
        if (Objects.isNull(basicService)) {
            throw new IllegalStateException("Service not found for entity: " + entityClass.getSimpleName());
        }

        basicService.add(entity);
        temporalEntityRepository.deleteById(temporalEntity.getId());
    }

    private BaseEntity createEntityFromTemporalXML(final TemporalEntity temporalEntity, final Class entityClass) {
        xmlValidator.validateXml(temporalEntity.getEntity(), temporalEntity.getEntityClass());
        return xmlMarshaller.fromXml(entityClass, temporalEntity.getEntity());
    }

    private BaseEntity createEntityFromTemporalJSON(final TemporalEntity temporalEntity, final Class entityClass) {
        return (BaseEntity) jsonMarshaller.fromJson(entityClass, temporalEntity.getEntity());
    }
}
