package com.project.holidaymanagementproject.controller;

import com.project.holidaymanagementproject.service.PublicHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vasilija Uzunova
 */
@Controller
@RequestMapping("/import")
public class ImportHolidayController {
    @Autowired
    private PublicHolidayService holidayService;

    @GetMapping("/webService")
    public String webService() {
        holidayService.webService();
        return "redirect:/holidays";
    }
}
