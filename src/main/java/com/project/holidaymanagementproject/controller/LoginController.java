package com.project.holidaymanagementproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vasilija Uzunova
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String goToLoginPage() {
        return "login";
    }

}
