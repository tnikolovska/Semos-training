package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.TypeOfUser;

import java.util.List;

public interface PersonService {
	void savePerson(String firstName,
					String lastName,
					String email,
					String typeOfUser,
					String activeStatus);

	void updatePerson(String dbId,
					  String firstName,
					  String lastName,
					  String email,
					  String typeOfUser,
					  String activeStatus);

	void deletePerson(int id);
	List<Person> getAll();

	Person getPersonByEmail(String email);
	Person getPersonByReferenceID(String referenceID);
	List<Person> getPeopleByName(String firstName);
	List<Person> getPeopleByLastName(String lastName);
	List<Person> getPeopleByType(TypeOfUser type);
	List<Person> getActivePeople();
    List<String> getAllReferenceIds();
    List<OffDay> getAllOffDays(String email);
    Person getPersonByDBId(int id);
    List<Person> returnFilteredList(String filterType, String filterValue);
}
