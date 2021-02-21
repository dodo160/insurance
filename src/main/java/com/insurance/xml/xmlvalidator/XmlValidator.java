package com.insurance.xml.xmlvalidator;

import javax.xml.bind.ValidationException;

public interface XmlValidator {

    void validateXml(String xml, String className) throws ValidationException;
}
