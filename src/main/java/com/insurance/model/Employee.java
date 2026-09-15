package com.insurance.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("EMPLOYEE")
@XmlRootElement()
@XmlType(namespace = "/insurance/model/employee")
public class Employee extends User {

    private static final long serialVersionUID = 6805141899916830921L;

    @Override
    public String toString() {
        return "Employee{ " + super.toString() + " }";
    }
}
