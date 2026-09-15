package com.insurance.xml;

import com.insurance.model.BaseEntity;

public interface XmlMarshaller {

    public <T extends BaseEntity> T fromXml(Class<T> entityClass, String xmlString);

    public <T extends BaseEntity> String toXml(Class<T> entityClass, BaseEntity entity);
}
