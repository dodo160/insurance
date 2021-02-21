package com.insurance.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "temporal_entity")
public class TemporalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String entityClass;

    private String mediaType;

    private String entity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemporalEntity)) return false;
        TemporalEntity that = (TemporalEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(entityClass, that.entityClass) &&
                Objects.equals(mediaType, that.mediaType) &&
                Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, entityClass, mediaType, entity);
    }

    @Override
    public String toString() {
        return "TemporalEntity{" +
                "id=" + id +
                ", user=" + user +
                ", entityClass='" + entityClass + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", entity='" + entity + '\'' +
                '}';
    }
}
