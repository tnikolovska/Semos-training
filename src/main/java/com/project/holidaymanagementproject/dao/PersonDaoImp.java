package com.project.holidaymanagementproject.dao;

import com.project.holidaymanagementproject.model.*;
import com.project.holidaymanagementproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class PersonDaoImp implements PersonDao {
    private static final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void savePerson(Person person) {
        Session session = sf.openSession();
        session.beginTransaction();
        String query = "from Role r where r.name = 'USER'";
        List<Role> roles = session.createQuery(query, Role.class).list();
        Role role = !roles.isEmpty() ? roles.get(0) : null;
        if (role != null) {
            person.addRole(role);
        }
        session.save(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updatePerson(Person person) {

        Session session = sf.openSession();
        session.beginTransaction();
        session.update(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Person getPersonByDBId(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Person person = session.find(Person.class, id);
        session.getTransaction().commit();
        session.close();
        return person;
    }

    @Override
    public void deletePerson(Person person) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(person);
        session.getTransaction().commit();
        session.close();
    }



    public static List<Person> getAllWithReferenceID() {
        String query = "from Employee";
        Session session = sf.openSession();
        session.beginTransaction();
        List<Employee> employeeList = session.createQuery(query, Employee.class).list();
        query = "from Associate ";
        List<Associate> associateList = session.createQuery(query,Associate.class).list();
        session.getTransaction().commit();
        session.close();
        return Stream.of(employeeList, associateList)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());

    }

    @Override
    public Person getPerson(String currentReferenceID) {
        return getAllWithReferenceID().stream()
                                      .filter(person -> person.getReferenceID().equals(currentReferenceID))
                                      .findFirst()
                                      .orElse(null);
    }

    @Override
    public List<Person> getAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Person> personList = session.createQuery("from Person", Person.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return personList;
    }

    @Override
    public Person getPersonByEmail(String email) {
        Session session = sf.openSession();
        session.beginTransaction();
        Person person = session.createQuery("from Person p where p.emailAddress = :email", Person.class)
                               .setParameter("email",email)
                               .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return person;
    }

    @Override
    public List<Person> getPeopleByName(String firstName) {

        String queryString = "from Person p where p.firstName = :first";
        Session session = sf.openSession();
        session.beginTransaction();

        List<Person> personList = session.createQuery(queryString, Person.class).setParameter("first", firstName).list();

        session.getTransaction().commit();
        session.close();
        return personList;
    }

    @Override
    public List<Person> getPeopleByLastName(String lastName) {

        String queryString = "from Person p where p.lastName = :last";
        Session session = sf.openSession();
        session.beginTransaction();

        List<Person> personList = session.createQuery(queryString, Person.class).setParameter("last", lastName).list();

        session.getTransaction().commit();
        session.close();
        return personList;
    }

    @Override
    public List<Person> getPeopleByType(TypeOfUser typeOfUser) {

        String queryString = "from Person p where p.typeOfUser = :type";
        Session session = sf.openSession();
        session.beginTransaction();

        List<Person> personList = session.createQuery(queryString, Person.class).setParameter("type", typeOfUser).list();

        session.getTransaction().commit();
        session.close();
        return personList;
    }

    @Override
    public List<Person> getActivePeople() {

        String queryString = "from Person p where p.activeStatus = true";
        Session session = sf.openSession();
        session.beginTransaction();

        List<Person> personList = session.createQuery(queryString, Person.class).list();

        session.getTransaction().commit();
        session.close();
        return personList;
    }

    //THIS METHOD IS CREATED TO TEST getReferenceID
    @Override
    public List<String> getAllReferenceIds() {
        Session session = sf.openSession();
        List<String> referenceIdList = session.createQuery("from Person ",Person.class).getResultList().stream()
                                              .map(Person::getReferenceID)
                                              .filter(Objects::nonNull)
                                              .collect(Collectors.toList());
        session.close();
        return referenceIdList;
    }

    @Override
    public List<OffDay> getAllOffDays(String email) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<OffDay> offDays = session.createQuery("from Person p left join fetch p.offDays where p.emailAddress = :currentEmail",Person.class)
                                      .setParameter("currentEmail",email).getSingleResult().getOffDays();
        session.getTransaction().commit();
        session.close();
        return offDays;
    }

}
