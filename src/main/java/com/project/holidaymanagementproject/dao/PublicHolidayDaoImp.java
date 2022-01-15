package com.project.holidaymanagementproject.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.holidaymanagementproject.model.LocalDateAdapter;
import com.project.holidaymanagementproject.model.PublicHoliday;
import com.project.holidaymanagementproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PublicHolidayDaoImp implements PublicHolidayDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private List<PublicHoliday> publicHolidays = new ArrayList<PublicHoliday>();

    @Override
    public void webService() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create("https://public-holiday.p.rapidapi.com/2021/MK"))
                                         .header("x-rapidapi-key", "4235056b37msh4a1ec1385b09a62p109cd4jsn8aabf31402f8")
                                         .header("x-rapidapi-host", "public-holiday.p.rapidapi.com")
                                         .method("GET", HttpRequest.BodyPublishers.noBody())
                                         .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenApply(this::parse)
              .join();
        Session session = sessionFactory.openSession();
        publicHolidays.stream().map(t -> save(t, session)).forEach(System.out::println);

    }

    public String parse(String responseBody) {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        PublicHoliday[] array = gson.fromJson(responseBody, PublicHoliday[].class);
        publicHolidays = Arrays.asList(array);
        return null;

    }

    public long save(PublicHoliday publicHoliday, Session session) {

        session.beginTransaction();
        session.save(publicHoliday);
        session.getTransaction().commit();
        return publicHoliday.getId();

    }

    @Override
    public void updateHoliday(PublicHoliday publicHoliday) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(publicHoliday);
        session.getTransaction().commit();
    }

    @Override
    public List<PublicHoliday> list() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<PublicHoliday> list = session.createQuery("from PublicHoliday").list();
        session.getTransaction().commit();
        return list;
    }

}
