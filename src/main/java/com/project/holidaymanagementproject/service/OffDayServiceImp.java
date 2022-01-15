package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.dao.OffDayDao;
import com.project.holidaymanagementproject.dao.PersonDao;
import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.PersonOffDay;
import com.project.holidaymanagementproject.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OffDayServiceImp implements OffDayService {
	@Autowired
	private OffDayDao offDayDao;
	@Autowired
	private PersonDao personDao;
	@Override
	public void registerOffDay(LocalDate startDate, LocalDate endDate, String description, int idPerson) {
		Person person = personDao.getPersonByDBId(idPerson);
		OffDay offDay = new OffDay(startDate,endDate, description,person);
		offDayDao.registerOffDay(offDay);
	}

	@Override
	public void updateOffDay(OffDay offDay) {
		LocalDate startDate = offDay.getStartDate();
		LocalDate endDate = offDay.getEndDate();
		int totalOffDaysRequested = (int) ChronoUnit.DAYS.between(startDate, endDate)+1;
		offDay.setOffdaysRequested(totalOffDaysRequested);
		Person currentPerson = getPerson(offDay);
		offDay.setPerson(currentPerson);
		offDayDao.updateOffDay(offDay);

	}

	@Override
	public void validation(OffDay offDay) {
		offDayDao.validation(offDay);

	}

	@Override
	public OffDay getOffDayById(int id) {
		return offDayDao.getOffDayById(id);
	}

	@Override
	public List<PersonOffDay> offDayFilter(String referenceId, String nameSurname, LocalDate date,LocalDate endDate, Status status) {
		return offDayDao.offDayFilter(referenceId, nameSurname, date,endDate, status);


	}

	@Override
	public Person getPerson(OffDay offday) {
		return offDayDao.getPerson(offday);
	}

}
