package com.project.holidaymanagementproject.controller;

import com.project.holidaymanagementproject.model.OffDay;
import com.project.holidaymanagementproject.service.PersonService;
import com.project.holidaymanagementproject.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Vasilija Uzunova
 */
@Controller
@RequestMapping("/personOffDay")
@PreAuthorize("hasAuthority('USER')")
public class PersonOffDayController {

    @Autowired
    PersonService service;

    @GetMapping("/getOffDayRequests")
    public String getOffDays (Model model) {
        return offDayPagination(0,model);
    }

    @GetMapping("/getOffDayRequests/page/{pageNo}")
    public String offDayPagination(@PathVariable(value = "pageNo") int pageNo, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        List<OffDay> offDays = service.getAllOffDays(email);
        int pageSize = 10;
        List<OffDay> offDaysSublist = PaginationUtil.getSublist(pageNo,pageSize,offDays);
        int totalOffDays = offDays.size();
        int pagesNo = totalOffDays / pageSize;
        int totalPages = PaginationUtil.getTotalPages(pageSize, totalOffDays);
        String redirectView ="/personOffDay/getOffDayRequests";
        if (pageNo > totalPages) return "redirect:" + redirectView;
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("redirectView", redirectView);
        model.addAttribute("offDayList", offDaysSublist);
        model.addAttribute("person", service.getPersonByEmail(email));
        return "user_view";
    }
}
