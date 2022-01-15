package com.project.holidaymanagementproject.controller;

import com.project.holidaymanagementproject.model.PublicHoliday;
import com.project.holidaymanagementproject.service.PublicHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PublicHolidayController {

    @Autowired
    private PublicHolidayService holidayService;

    @GetMapping("holidays")
    public String listPublicHolidays(Model model) {
        List<PublicHoliday> holidays = holidayService.listPublicHolidays();
        model.addAttribute("holidayList", holidays);
        if (holidays == null || holidays.isEmpty()) {
            model.addAttribute("noHolidays", true);
        }
        return "holidays";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updatePublicHoliday")
    public void updatePublicHoliday(@RequestBody PublicHoliday publicHoliday) {
        holidayService.updateHoliday(publicHoliday);

    }

}

