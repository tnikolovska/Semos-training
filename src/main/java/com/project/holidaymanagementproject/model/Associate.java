package com.project.holidaymanagementproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Associate")
public class Associate extends Person {
    @Column(name = "reference_id", length = 4)
    private String referenceID;
    private static final long serialVersionUID = 1L;

    public Associate(String firstName, String lastName, TypeOfUser typeOfUser, boolean activeStatus, String emailAddress) {
        super(firstName, lastName, typeOfUser, activeStatus, emailAddress);
    }

    @Override
    public String getReferenceID() {
        return this.referenceID;
    }

    @Override
    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }
}
