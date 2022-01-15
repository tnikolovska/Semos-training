package com.project.holidaymanagementproject.dao;

import com.project.holidaymanagementproject.model.PublicHoliday;

import java.util.List;

public interface PublicHolidayDao {
	void webService();
	void updateHoliday(PublicHoliday publicHoliday);
	List<PublicHoliday> list();
	

}
