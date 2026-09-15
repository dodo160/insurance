package com.insurance.listener;

import com.insurance.model.BaseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

public class BaseEntityListener {

    private static final Logger LOGGER = LogManager.getLogger(BaseEntityListener.class);

    @PrePersist
    void onPrePersist(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is prepare to persist with id: {}", entity.getClass().getName(), entity.getId());
    }

    @PostPersist
    void onPostPersist(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is persisted with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PostLoad
    void onPostLoad(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is loaded with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PreUpdate
    void onPreUpdate(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is prepare to update with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PostUpdate
    void onPostUpdate(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is updated with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PreRemove
    void onPreRemove(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is prepare to remove with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }

    @PostRemove
    void onPostRemove(final BaseEntity entity) {
        LOGGER.debug("DEBUG: {} is removed with id: {}", entity.getClass().getSimpleName(), entity.getId());
    }
}
