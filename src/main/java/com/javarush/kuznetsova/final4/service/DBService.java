package com.javarush.kuznetsova.final4.service;

import com.javarush.kuznetsova.final4.dao.CityDAO;
import com.javarush.kuznetsova.final4.dao.CountryDAO;
import com.javarush.kuznetsova.final4.entity.City;
import com.javarush.kuznetsova.final4.entity.Country;
import com.javarush.kuznetsova.final4.entity.CountryLanguage;
import com.javarush.kuznetsova.final4.util.HiberUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class DBService {
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;

    public DBService(CityDAO cityDAO, CountryDAO countryDAO) {
        this.cityDAO = cityDAO;
        this.countryDAO = countryDAO;
    }

    public List<City> fetchData() {
        Session session = HiberUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            List<Country> countries = countryDAO.getAll(session);
            List<City> allCities = new ArrayList<>();
            int totalCount = cityDAO.getTotalCount(session);
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(cityDAO.getItems(session, i, step));
            }
            session.getTransaction().commit();
            return allCities;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка чтения всех данных из бд");
            throw e;
        }
    }
    public void testMysqlData(List<Integer> ids) {
        Session session = HiberUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            for (Integer id : ids) {
                City city = cityDAO.getById(session, id);
                 Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка чтения из бд");
            throw e;
        }
    }
}
