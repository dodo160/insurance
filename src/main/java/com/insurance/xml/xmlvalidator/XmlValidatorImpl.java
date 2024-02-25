package com.insurance.xml.xmlvalidator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.ValidationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

@Component
public class XmlValidatorImpl implements XmlValidator {

    @Value("${xsd.schema.path}")
    private String xsdPath;

    @Override
    public void validateXml(final String xml, final String className) throws ValidationException {
        try {
            final SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = factory.newSchema(new File(xsdPath + className + ".xsd"));
            final Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
        } catch (SAXException | IOException e) {
            throw new ValidationException(e);
        }
    }
}
