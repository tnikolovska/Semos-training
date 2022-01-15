package com.project.holidaymanagementproject.util;

import com.project.holidaymanagementproject.exception.NoFreeSpaceException;
import com.project.holidaymanagementproject.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonUtil {
    public static String generateRandomPassword() {
        List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1));

        PasswordGenerator passGenerator = new PasswordGenerator();
        return passGenerator.generatePassword(8, rules);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getAllReferenceID() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        String query = "select referenceID from Employee where referenceID is NOT null";
        Session session = sf.openSession();
        session.beginTransaction();
        List<String> employeeReferenceIDs = session.createQuery(query).list();
        query = "select referenceID from Associate  where referenceID is not null";
        List<String> associateReferenceIDs = session.createQuery(query).list();
        session.getTransaction().commit();
        //HibernateUtil.shutdown();
        return Stream.of(employeeReferenceIDs, associateReferenceIDs)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }

    public static String generateRandomReferenceId() throws NoFreeSpaceException {
        List<String> refferenceIDs = getAllReferenceID();
        if (refferenceIDs.size() == 9999)
            throw new NoFreeSpaceException("There is no free space to create new user.");
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        if (refferenceIDs.contains(id)) generateRandomReferenceId();
        return id;
    }

    public static Person createPersonObject(String firstName, String lastName, String email, String typeOfUser, String activeStatus) {
        TypeOfUser userType = TypeOfUser.valueOf(typeOfUser);
        Person person;
        boolean isActive = activeStatus.equals("true");
        switch (userType) {
            case EMPLOYEE:
                person = new Employee(firstName, lastName, userType, isActive, email);
                break;
            case ASSOCIATE:
                person = new Associate(firstName, lastName, userType, isActive, email);
                break;
            default:
                person = new Guest(firstName, lastName, userType, isActive, email);
                break;
        }
        try {
            if (!person.getTypeOfUser().equals(TypeOfUser.GUEST))
                person.setReferenceID(PersonUtil.generateRandomReferenceId());
        } catch (NoFreeSpaceException e) {
            System.out.println(e);
        }
        return person;
    }

}
