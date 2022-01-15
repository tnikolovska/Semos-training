package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.dao.PersonDao;
import com.project.holidaymanagementproject.exception.NoFreeSpaceException;
import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.TypeOfUser;
import com.project.holidaymanagementproject.util.PersonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImp implements PersonService {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void savePerson(String firstName, String lastName, String email, String typeOfUser, String activeStatus) {
        Person currentPerson = PersonUtil.createPersonObject(firstName, lastName, email, typeOfUser, activeStatus);
        String generatedPassword = PersonUtil.generateRandomPassword();
        emailService.sendEmail(currentPerson, generatedPassword);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        currentPerson.setGeneratedPassword(encoder.encode(generatedPassword));
        personDao.savePerson(currentPerson);
    }

    @Override
    @Transactional
    public void updatePerson(String dbId,
                             String firstName,
                             String lastName,
                             String email,
                             String typeOfUser,
                             String activeStatus) {
        int id = Integer.parseInt(dbId);
        Person dbPerson = personDao.getPersonByDBId(id);
        Person currentPerson = PersonUtil.createPersonObject(firstName, lastName, email, typeOfUser, activeStatus);
        currentPerson.setId(id);
        if (!currentPerson.getTypeOfUser().equals(TypeOfUser.GUEST)) {
            try {
                String referenceId = dbPerson.getReferenceID() == null ? PersonUtil.generateRandomReferenceId() : dbPerson
                        .getReferenceID();
                currentPerson.setReferenceID(referenceId);
            } catch (NoFreeSpaceException e) {
                System.out.println(e);
            }
        }
        currentPerson.setRoles(dbPerson.getRoles());
        currentPerson.setDays(dbPerson.getDays());
        currentPerson.setOffDays(dbPerson.getOffDays());
        currentPerson.setGeneratedPassword(dbPerson.getGeneratedPassword());
        personDao.updatePerson(currentPerson);
    }

    @Override
    public void deletePerson(int id) {
        Person person = personDao.getPersonByDBId(id);
        personDao.deletePerson(person);
    }

    @Override
    public List<Person> getAll() {
        return personDao.getAll();
    }


    @Override
    public Person getPersonByEmail(String email) {
        return personDao.getPersonByEmail(email);
    }

    @Override
    @Transactional
    public Person getPersonByReferenceID(String referenceID) {
        return personDao.getPerson(referenceID);
    }

    @Override
    @Transactional
    public List<Person> getPeopleByName(String firstName) {
        return personDao.getPeopleByName(firstName);
    }

    @Override
    @Transactional
    public List<Person> getPeopleByLastName(String lastName) {
        return personDao.getPeopleByLastName(lastName);
    }

    @Override
    @Transactional
    public List<Person> getPeopleByType(TypeOfUser type) {
        return personDao.getPeopleByType(type);
    }

    @Override
    @Transactional
    public List<Person> getActivePeople() {
        return personDao.getActivePeople();
    }

    @Override
    public List<String> getAllReferenceIds() {
        return personDao.getAllReferenceIds();
    }

    @Override
    public List<OffDay> getAllOffDays(String email) {
        return personDao.getAllOffDays(email);
    }

    @Override
    public Person getPersonByDBId(int id) {
        return personDao.getPersonByDBId(id);
    }

    @Override
    public List<Person> returnFilteredList(String filterType, String filterValue) {
        switch (filterType) {
            case "firstName":
                return getPeopleByName(filterValue);
            case "lastName":
                return getPeopleByLastName(filterValue);
            case "referenceID": {
                Person person = getPersonByReferenceID(filterValue);
                return (person == null) ? new ArrayList<>() : List.of(person);
            }
            default:
                return getAll();
        }
    }

}
