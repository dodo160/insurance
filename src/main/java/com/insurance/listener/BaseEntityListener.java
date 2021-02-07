package com.insurance.listener;

import com.insurance.model.BaseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

public class BaseEntityListener {

    final Logger LOGGER = LogManager.getLogger(BaseEntityListener.class);

    @PrePersist
    void onPrePersist(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is prepare to persist with id: %d", entity.getClass().getName(), entity.getId()));
    }

    @PostPersist
    void onPostPersist(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is persisted with id: %d", entity.getClass().getSimpleName(), entity.getId()));
    }

    @PostLoad
    void onPostLoad(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is loaded with id: %d", entity.getClass().getSimpleName(), entity.getId()));
    }

    @PreUpdate
    void onPreUpdate(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is prepare to update with id: %d", entity.getClass().getSimpleName(), entity.getId()));
    }

    @PostUpdate
    void onPostUpdate(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is updated with id: %d", entity.getClass().getSimpleName(), entity.getId()));
    }

    @PreRemove
    void onPreRemove(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is prepare to remove with id: %d", entity.getClass().getSimpleName(), entity.getId()));
    }

    @PostRemove
    void onPostRemove(final BaseEntity entity) {
        LOGGER.info(String.format("INFO: %s is removed with id: %d", entity.getClass().getSimpleName(), entity.getId()));
    }
}
