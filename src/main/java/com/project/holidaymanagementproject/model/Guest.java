package com.project.holidaymanagementproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@Table(name = "guest")
@Getter
@DiscriminatorValue("Guest")
@NoArgsConstructor
public class Guest extends Person{
	private static final long serialVersionUID = 1L;

	public Guest(String firstName, String lastName, TypeOfUser typeOfUser, boolean activeStatus, String emailAddress) {
		super(firstName, lastName, typeOfUser, activeStatus, emailAddress);
	}

	@Override
	public String getReferenceID() {
		return null;
	}

	@Override
	public void setReferenceID(String referenceID){
	}

}
