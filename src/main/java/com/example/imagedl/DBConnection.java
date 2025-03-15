package com.example.imagedl;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Setter
@Getter
@Entity
@Table(name = "logs")

public class DBConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   private Long id;

    @Column(name = "request")   private String request;

    @Column(name = "results")    private int results;

    @Column(name = "urls", length=10485760)    private String urls;

    // Getters and setters, constructors, etc.

    public static void saveLog(String request, int results, String urls) {
        //SessionFactory sessionFactory = new Configuration().configure("/application.yml").buildSessionFactory();
        //SessionFactory sessionFactory = new Configuration().buildSessionFactory();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("application.properties"));
        } catch (IOException e) {
            System.out.println("File not found" + e.getMessage());
        }
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .addProperties(properties)
                .addAnnotatedClass(Thread.currentThread().getContextClassLoader().getClass())
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        DBConnection dbConnection = new DBConnection();
        dbConnection.setRequest(request);
        dbConnection.setResults(results);
        dbConnection.setUrls(urls);
        session.getTransaction().begin();
        session.persist(dbConnection);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();}
}