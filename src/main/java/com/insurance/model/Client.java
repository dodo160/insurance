package com.insurance.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("CLIENT")
@XmlRootElement()
@XmlType(namespace = "/insurance/model/client")
public class Client extends User{

    @Override
    public String toString() {
        return "Client{ " + super.toString() + " }";
    }
}
