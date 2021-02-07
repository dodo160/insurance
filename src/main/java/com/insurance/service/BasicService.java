package com.insurance.service;

import javax.xml.bind.ValidationException;
import java.util.Set;

public interface BasicService<T,ID> {

    Set<T> findAll();

    T findById(ID id);

    T add(T entity) throws ValidationException;

    T update(T entity) throws ValidationException;

    void deleteById(ID id);

    void softDeleteById(ID id);
}
