package com.insurance.common.xml;

import com.insurance.common.model.BaseEntity;

public interface XmlMarshaller {

    public <T> T fromXml(Class<T> entityClass, String xmlString);

    public <T> String toXml(Class<T> entityClass, BaseEntity entity);
}
