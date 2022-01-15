package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.PersonOffDay;
import com.project.holidaymanagementproject.model.Status;

import java.time.LocalDate;
import java.util.List;

//import org.graalvm.compiler.nodes.memory.address.OffsetAddressNode;

public interface OffDayService {
	void registerOffDay(LocalDate startDate, LocalDate endDate, String description, int idPerson);
	void updateOffDay(OffDay offDay);
	void validation(OffDay offDay);
	OffDay getOffDayById(int id);
	List<PersonOffDay> offDayFilter(String referenceId,String nameSurname, LocalDate date,LocalDate endDate, Status status);
	Person getPerson(OffDay offDay);
	//List<OffDay> getAllOffDayRequests();
}
