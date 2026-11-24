package com.insurance.common.xml;

import com.insurance.common.exception.XmlMarshallingException;
import com.insurance.common.model.BaseEntity;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class XmlMarshallerImpl implements XmlMarshaller {

    private final Map<Class<?>, JAXBContext> contexts = new ConcurrentHashMap<>();

    public <T extends BaseEntity> T fromXml(final Class<T> entityClass, final String xmlString) {
        try {
            final JAXBContext jaxbContext = getContext(entityClass);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return entityClass.cast(unmarshaller.unmarshal(new StreamSource(new StringReader(xmlString))));
        } catch (JAXBException e) {
            throw new XmlMarshallingException("Unable to deserialize XML to " +
                    entityClass.getSimpleName(), e);
        }
    }

    public <T extends BaseEntity> String toXml(final Class<T> entityClass, final BaseEntity entity) {
        try {
            final JAXBContext jaxbContext = getContext(entityClass);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(entity, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new XmlMarshallingException("Unable to serialize " +
                    entityClass.getSimpleName() +
                    " to XML", e);
        }
    }

    private <T extends BaseEntity> JAXBContext getContext(Class<T> clazz) {
        return contexts.computeIfAbsent(
                clazz,
                key -> {
                    try {
                        return JAXBContext.newInstance(key);
                    } catch (JAXBException e) {
                        throw new XmlMarshallingException("Unable to create JAXB context for "
                                + key.getSimpleName(), e);
                    }
                }
        );
    }
}
