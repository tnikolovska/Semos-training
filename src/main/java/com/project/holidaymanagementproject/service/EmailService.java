package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.model.Person;

public interface EmailService {
    public void sendEmail(Person person, String password);
}
