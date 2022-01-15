package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmail(Person person, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("semos.training.offday@gmail.com");
        message.setTo(person.getEmailAddress());
        message.setSubject("Password");
        message.setText(String.format("Hello %s,\nYour password is: %s.",person.getFirstName(), password));
        javaMailSender.send(message);
    }
}
