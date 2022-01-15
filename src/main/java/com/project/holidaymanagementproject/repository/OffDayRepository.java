package com.project.holidaymanagementproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.holidaymanagementproject.model.OffDay;
@Repository
public interface OffDayRepository extends JpaRepository<OffDay, Integer> {

}
