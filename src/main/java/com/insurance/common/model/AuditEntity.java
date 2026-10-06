package com.insurance.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class AuditEntity extends BaseEntity {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    private LocalDateTime deletedDate;

    @XmlTransient
    @JsonIgnore
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    @JsonIgnore
    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @XmlTransient
    @JsonIgnore
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), createdDate, lastUpdatedDate, deletedDate);
    }

    @Override
    public String toString() {
        return "AuditEntity{" +
                "createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", deletedDate=" + deletedDate +
                "} " + super.toString();
    }
}
