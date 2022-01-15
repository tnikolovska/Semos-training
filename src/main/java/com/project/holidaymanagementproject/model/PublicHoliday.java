package com.project.holidaymanagementproject.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity()
@Table(name="publicholidays")
@Data
//@Builder
public class PublicHoliday {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	@Column(name="date")
	private LocalDate date;
	@Column(name="local_name")
	private String localName;
	@Column(name="name")
	private String name;
	@Column(name="country_code")
	public String countryCode;
	@Column(name="fixed")
	public boolean fixed;
	@Column(name="global")
	public boolean global;
	@Column(name="counties")
	public String counties;
	@Column(name="launch_year")
	public String launchYear;
	@Column(name="type")
	public String type;
	public PublicHoliday() {} 
	
}
