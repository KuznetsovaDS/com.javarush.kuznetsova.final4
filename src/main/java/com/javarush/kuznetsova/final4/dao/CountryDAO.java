package com.javarush.kuznetsova.final4.dao;

import com.javarush.kuznetsova.final4.entity.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CountryDAO {

    public List<Country> getAll(Session session) {
        Query<Country> query = session.createQuery("select c from Country c join fetch c.languages", Country.class);
        return query.list();
    }
}
