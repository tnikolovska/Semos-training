package com.project.holidaymanagementproject.service;

import java.util.List;

import org.springframework.ui.Model;

import com.project.holidaymanagementproject.model.PublicHoliday;

public interface PublicHolidayService {
	void webService();
	void updateHoliday(PublicHoliday publicHoliday);
	List<PublicHoliday> listPublicHolidays();
}
