package com.haulmont.testtask.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import com.haulmont.testtask.model.*;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    private HibernateConfig() {}

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Doctor.class);
            configuration.addAnnotatedClass(Patient.class);
            configuration.addAnnotatedClass(Priority.class);
            configuration.addAnnotatedClass(Prescription.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

        }

        return sessionFactory;
    }

}