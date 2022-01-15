package com.project.holidaymanagementproject.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder 
@Data
public class PersonOffDay {
	private String referenceID;
	private String name;
	private String surname;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
	private Status status;
	private int numberOfDAysRequested;
	private int numberOfDaysLeft;
	private int offDayId;
}
