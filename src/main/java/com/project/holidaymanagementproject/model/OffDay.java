package com.project.holidaymanagementproject.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="off_day")
@Data
public class OffDay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="start_date")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="end_date")
	private LocalDate endDate;
	@Column(name="description")
	private String description;
	@Column(name="status", length = 50,columnDefinition = "varchar(50) default 'REQUESTED'")
	@Enumerated(value=EnumType.STRING)
	//@Builder.Default
	private Status status=Status.REQUESTED;
	@ManyToOne(targetEntity = Person.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="id_person")
	private Person person;
	@Column(name="off_days_requested")
	private int offdaysRequested;

	public OffDay() {}

	public OffDay(LocalDate startDate, LocalDate endDate, String description, Person person) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.person = person;
		this.offdaysRequested = (int) ChronoUnit.DAYS.between(startDate, endDate);
	}
}
