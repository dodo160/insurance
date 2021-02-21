package com.insurance.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("EMPLOYEE")
@XmlRootElement()
@XmlType(namespace = "/insurance/model/employee")
public class Employee extends User{

    @Override
    public String toString() {
        return "Employee{ " + super.toString() + " }";
    }
}
