package com.example.imagedl;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
@Table(name = "logs")

public class DBConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   private Long id;

    @Column(name = "request")    private String request;

    @Column(name = "results")    private int results;

    @Column(name = "urls", length=10485760)    private String urls;

    // Getters and setters, constructors, etc.

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRequest() { return request; }

    public void setRequest(String request) { this.request = request; }

    public int getResults() { return results; }

    public void setResults(int results) { this.results = results; }

    public String getUrls() { return urls; }

    public void setUrls(String urls) { this.urls = urls; }

    public static void saveLog(String request, int results, String urls) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        DBConnection dbConnection = new DBConnection();
        dbConnection.setRequest(request);
        dbConnection.setResults(results);
        dbConnection.setUrls(urls);
        session.getTransaction().begin();
        session.save(dbConnection);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}