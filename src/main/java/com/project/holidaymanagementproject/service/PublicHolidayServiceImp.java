package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.dao.PublicHolidayDao;
import com.project.holidaymanagementproject.model.PublicHoliday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional(readOnly=true)
public class PublicHolidayServiceImp implements PublicHolidayService{
	@Autowired
	private PublicHolidayDao holidayDao;

	public void webService() {
		holidayDao.webService();
		
	}

	@Override
	public void updateHoliday(PublicHoliday publicHoliday) {
		holidayDao.updateHoliday(publicHoliday);
	}
	@Override
	public List<PublicHoliday> listPublicHolidays()
	{
		return holidayDao.list();
		
	}


	
	
	

	
	

}
