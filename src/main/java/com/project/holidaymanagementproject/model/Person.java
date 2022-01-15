package com.project.holidaymanagementproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name="person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when type_of_user = 'EMPLOYEE' then 'Employee'" +
		" when type_of_user = 'ASSOCIATE' then 'Associate' else 'Guest' end")
@Getter
@Setter
public abstract class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "type_of_user")
	@Setter
	@Enumerated(EnumType.STRING)
	private TypeOfUser typeOfUser;
	@Column(name = "active_status")
	private boolean activeStatus;
	@Column(name = "email_address")
	private String emailAddress;
	@Setter
	@Column(name = "user_password")
	private String generatedPassword;
	@Column(name="days",columnDefinition = "int default 20")
	private int days=20;
	@OneToMany(mappedBy = "person", fetch= FetchType.EAGER)
	private List<OffDay> offDays;

	private static final long serialVersionUID = 1853373104008093820L;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")) // this means that by adding this, when a role is added to the hash then it will be added to the user_role table with the user
	private Set<Role> roles = new HashSet<>();

	public Person(String firstName, String lastName, TypeOfUser typeOfUser, boolean activeStatus, String emailAddress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.typeOfUser = typeOfUser;
		this.activeStatus = activeStatus;
		this.emailAddress = emailAddress;
	}

	public void addRole(Role role) {
		roles.add(role);
	}
	public abstract String getReferenceID();
	public abstract void setReferenceID(String referenceID);

}
