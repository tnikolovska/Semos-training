package com.project.holidaymanagementproject.dao;

import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.TypeOfUser;

import java.util.List;


public interface PersonDao {

	void savePerson(Person person);
	void updatePerson(Person person);
	Person getPersonByDBId(int id);
	Person getPerson(String currentReferenceID);
	List<Person> getAll();
	Person getPersonByEmail(String email);


	List<Person> getPeopleByName(String firstName);
	List<Person> getPeopleByLastName(String lastName);
	List<Person> getPeopleByType(TypeOfUser type);
	List<Person> getActivePeople();


	//THIS METHOD IS CREATED TO TEST getRefferenceID
	List<String> getAllReferenceIds();
	List<OffDay> getAllOffDays(String email);


	void deletePerson(Person person);
}
