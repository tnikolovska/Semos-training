package com.project.holidaymanagementproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.holidaymanagementproject.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
