package com.insurance.temporal.model;

import com.insurance.user.model.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "temporal_entity")
public class TemporalEntity implements Serializable {

    private static final long serialVersionUID = 4981381595897438184L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull(message = "Missing user")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Missing entity class")
    private String entityClass;

    @NotBlank(message = "Missing media type")
    private String mediaType;

    @NotBlank(message = "Missing entity")
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
