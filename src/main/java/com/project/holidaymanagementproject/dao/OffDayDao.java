package com.project.holidaymanagementproject.dao;

import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.PersonOffDay;
import com.project.holidaymanagementproject.model.Status;

import java.time.LocalDate;
import java.util.List;

public interface OffDayDao {
	void registerOffDay(OffDay offDay);
	void updateOffDay(OffDay offDay);
	void validation(OffDay offDay);
	OffDay getOffDayById(int id);
	List<PersonOffDay> offDayFilter(String referenceId,String nameSurname, LocalDate date,LocalDate endDate, Status status);
	Person getPerson(OffDay offDay);
	//List<OffDay> allOffDayRequests();
}

