package com.insurance.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User{

    @Override
    public String toString() {
        return "Employee{ " + super.toString() + " }";
    }
}
