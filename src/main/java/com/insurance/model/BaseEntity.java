package com.insurance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insurance.listener.BaseEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @XmlTransient
    @JsonIgnore
    public boolean isNew() {
        return Objects.isNull(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
