package com.insurance.common.model.listener;

import com.insurance.common.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class BaseEntityListener {

    @PrePersist
    void onPrePersist(final BaseEntity entity) {
        log.debug("DEBUG: {} is prepare to persist with id: {}", entity.getClass().getName(), entity.getId());
    }

    @PostPersist
    void onPostPersist(final BaseEntity entity) {
        log.debug("DEBUG: {} is persisted with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PostLoad
    void onPostLoad(final BaseEntity entity) {
        log.debug("DEBUG: {} is loaded with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PreUpdate
    void onPreUpdate(final BaseEntity entity) {
        log.debug("DEBUG: {} is prepare to update with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PostUpdate
    void onPostUpdate(final BaseEntity entity) {
        log.debug("DEBUG: {} is updated with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PreRemove
    void onPreRemove(final BaseEntity entity) {
        log.debug("DEBUG: {} is prepare to remove with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PostRemove
    void onPostRemove(final BaseEntity entity) {
        log.debug("DEBUG: {} is removed with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }
}
