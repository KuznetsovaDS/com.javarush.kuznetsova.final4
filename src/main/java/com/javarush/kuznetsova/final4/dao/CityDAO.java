package com.javarush.kuznetsova.final4.dao;

import com.javarush.kuznetsova.final4.entity.City;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO {

    public List<City> getItems(Session session, int offset, int limit) {
        Query<City> query = session.createQuery("select c from City c", City.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    public int getTotalCount(Session session) {
        Query<Long> query = session.createQuery("select count(c) from City c", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    public City getById(Session session, Integer id) {
        Query<City> query = session.createQuery("select c from City c join fetch c.country where c.id = :ID", City.class);
        query.setParameter("ID", id);
        return query.getSingleResult();
    }
}
