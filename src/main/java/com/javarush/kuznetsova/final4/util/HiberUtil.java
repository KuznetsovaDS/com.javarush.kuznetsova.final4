package com.javarush.kuznetsova.final4.util;

import com.javarush.kuznetsova.final4.entity.*;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HiberUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(City.class);
        configuration.addAnnotatedClass(CountryLanguage.class);
        configuration.addAnnotatedClass(Country.class);
        return configuration.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
       sessionFactory.close();
    }
}
