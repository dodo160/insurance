package com.insurance.common.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface BasicService<T, ID> {

    List<T> findAll();

    T findById(@NotNull ID id);

    T add(@Valid @NotNull T entity);

    T update(@NotNull ID id, @Valid @NotNull T entity);

    void deleteById(@NotNull ID id);

    void softDeleteById(@NotNull ID id);

    Class<T> getEntityServiceClass();
}
