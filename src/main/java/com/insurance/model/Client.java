package com.insurance.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User{

    @Override
    public String toString() {
        return "Client{ " + super.toString() + " }";
    }
}
