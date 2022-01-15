package com.project.holidaymanagementproject.repository;

import com.project.holidaymanagementproject.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Vasilija Uzunova
 */
@Repository
public interface UserRepository extends JpaRepository<Person, Integer> {

    @Query("from Person where emailAddress=?1")
    Person findByUsername(String username);
}
