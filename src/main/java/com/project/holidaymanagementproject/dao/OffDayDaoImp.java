package com.project.holidaymanagementproject.dao;

import com.project.holidaymanagementproject.exception.NotAllowedDateException;
import com.project.holidaymanagementproject.model.*;
import com.project.holidaymanagementproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Repository
public class OffDayDaoImp implements OffDayDao {
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	private List<PublicHoliday> list;
	private Map<LocalDate, PublicHoliday> map1 = new HashMap<LocalDate, PublicHoliday>();
	@Autowired
	private PublicHolidayDao publicHolidayDao;
	@Autowired
	private PersonDao personDao;
	@Override
	public void registerOffDay(OffDay offDay) {
		if(offDay.getStartDate() != null) {
			if(offDay.getStartDate().isAfter(LocalDate.now())) {
				if(offDay.getEndDate() != null)
				{
					try{
						if(offDay.getEndDate().isAfter(offDay.getStartDate())
								|| offDay.getStartDate().equals(offDay.getEndDate()))
						{
							Session session=sessionFactory.openSession();
							session.beginTransaction();
							offDay.setStatus(Status.REQUESTED);
							session.save(offDay);
							session.getTransaction().commit();
							session.close();
						}
						else {
							throw new NotAllowedDateException();

						}

					}catch(NotAllowedDateException e) {
						System.out.println("End date must be after start date");

					}

				}
				else if(offDay.getEndDate() == null)
				{
					Session session = sessionFactory.openSession();
					session.beginTransaction();
					offDay.setEndDate(offDay.getStartDate());
					offDay.setStatus(Status.REQUESTED);
					session.save(offDay);
					session.getTransaction().commit();
					session.close();

				}

			}
		}
	}

	@Override
	public void updateOffDay(OffDay offDay) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(offDay);
		session.getTransaction().commit();
		session.close();
	}
	@Override
	public void validation(OffDay offDay) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		list=publicHolidayDao.list();
		map1=list.stream().collect(Collectors.toMap(PublicHoliday::getDate,Function.identity(),(oldValue,newValue)->newValue));
		LocalDate startDate=offDay.getStartDate();
		LocalDate endDate=offDay.getEndDate();
		List<LocalDate> filteredList=startDate.datesUntil(endDate.plusDays(1)).filter(p->checkDate(p)).collect(Collectors.toList());
		int days=0;
		days=filteredList.size();
		Person person = getPerson(offDay);
		if(person.getDays()>=days) {
			person.setDays(person.getDays()-days);
			offDay.setOffdaysRequested(days);
			offDay.setStatus(Status.APPROVED);
			updateOffDay(offDay);
			personDao.updatePerson(person);
		}
		else {
			offDay.setStatus(Status.DECLINED);
			updateOffDay(offDay);
		}
		session.getTransaction().commit();
		session.close();
	}


	@Override
	public OffDay getOffDayById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		OffDay offDay = session.find(OffDay.class, id);
		session.getTransaction().commit();
		session.close();
		return offDay;
	}

	public boolean checkDate(LocalDate date) {
		if(!map1.containsKey(date)) {
			if(!date.getDayOfWeek().equals(DayOfWeek.SATURDAY)||!date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				return true;
			}
			else return false;
		}
		else return false;

	}

	@Transactional
	@Override
	public List<PersonOffDay> offDayFilter(@RequestParam String referenceId, @RequestParam String nameSurname, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam Status status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<PersonOffDay> offdalDaysFiltered=new ArrayList<PersonOffDay>();

		String queryString="SELECT p.reference_id, p.first_name, p.last_name, o.start_date, o.end_date, o.status, o.description, o.off_days_requested, p.days, o.id from off_day o, person p where o.id_person=p.id ";
		if(referenceId!=null) {
			queryString+=" and p.reference_id = ? ";
		}
		if(nameSurname!=null) {
			queryString+=" and ( p.first_name = ? or p.last_name= ? ) ";
		}
		if(date!=null) {
			queryString+=" and o.start_date >= ? ";
		}
		if(endDate!=null) {
			queryString+=" and o.end_date <= ? ";
		}
		if(status!=null) {
			queryString+=" and o.status = ? ";
		}

		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","postgres");
			PreparedStatement statement = connection.prepareStatement(queryString);
			int num = 0;
			if(referenceId != null) {
				num += 1;
				statement.setString(num, referenceId);
			}
			if(nameSurname!=null) {
				num += 1;
				statement.setString(num, nameSurname);
				num += 1;
				statement.setString(num, nameSurname);
			}
			if(date != null) {
				num += 1;
				statement.setDate(num, Date.valueOf(date));
			}
			if(endDate != null) {
				num += 1;
				statement.setDate(num, Date.valueOf(endDate));
			}
			if(status != null) {
				num += 1;
				statement.setString(num,String.valueOf(status.toString()));
			}
			ResultSet set=statement.executeQuery();

			while (set.next()) {
				PersonOffDay personOffDay=PersonOffDay.builder().referenceID(set.getString(1)).name(set.getString(2)).surname(set.getString(3)).startDate(set.getDate(4).toLocalDate()).endDate(set.getDate(5).toLocalDate()).status(Status.valueOf(set.getString(6))).description(set.getString(7)).numberOfDAysRequested(set.getInt(8)).numberOfDaysLeft(set.getInt(9)).offDayId(set.getInt(10)).build();
				offdalDaysFiltered.add(personOffDay);
			}
			set.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		session.close();
		return offdalDaysFiltered;

	}


	@Override
	public Person getPerson(OffDay offDay) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Person person = session.createQuery("from OffDay o left join fetch o.person where o.id = :id",OffDay.class).setParameter("id",offDay.getId()).getSingleResult().getPerson();
		session.getTransaction().commit();
		session.close();
		return person;
	}

	//	@Override
	//	public List<OffDay> allOffDayRequests() {
	//		Session session = sessionFactory.openSession();
	//		session.beginTransaction();
	//		List<OffDay> offDays = session.createQuery("from OffDay",OffDay.class).getResultList();
	//		session.getTransaction().commit();
	//		session.close();
	//		return offDays;
	//	}
}
