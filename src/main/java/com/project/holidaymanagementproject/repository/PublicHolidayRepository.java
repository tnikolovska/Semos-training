package com.project.holidaymanagementproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.holidaymanagementproject.model.PublicHoliday;
@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday,Long>{

}
