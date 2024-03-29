package com.insurance.xml;

import com.insurance.model.BaseEntity;
import org.springframework.stereotype.Component;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class XmlMarshallerImpl implements XmlMarshaller{


    public Object fromXml(final Class entityClass, final String xmlString) throws ValidationException {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return entityClass.cast(unmarshaller.unmarshal(new StreamSource(new StringReader(xmlString))));
        } catch (JAXBException e) {
            throw new ValidationException(e);
        }
    }

    public String toXml(final Class entityClass, final BaseEntity entity) throws ValidationException {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(entity, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new ValidationException(e);
        }
    }
}
