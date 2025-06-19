package com.javarush.kuznetsova.final4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kuznetsova.final4.dao.*;
import com.javarush.kuznetsova.final4.entity.City;
import com.javarush.kuznetsova.final4.mapper.CityCountryMapper;
import com.javarush.kuznetsova.final4.redis.CityCountry;
import com.javarush.kuznetsova.final4.service.*;
import com.javarush.kuznetsova.final4.util.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class App {
    public static void main(String[] args) {

    ObjectMapper mapper = new ObjectMapper();
    CityDAO cityDAO = new CityDAO();
    CountryDAO countryDAO = new CountryDAO();
    DBService cityService = new DBService(cityDAO, countryDAO);
    RedisService redisService = new RedisService(RedisUtil.getRedisClient(), mapper);

        try {
            List<City> allCities = cityService.fetchData();
            List<CityCountry> cityCountries = CityCountryMapper.INSTANCE.toCityCountryList(allCities);

            redisService.pushToRedis(cityCountries);
            List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

            long startRedis = System.currentTimeMillis();
            redisService.testRedisData(ids);
            long stopRedis = System.currentTimeMillis();

            long startMysql = System.currentTimeMillis();
            cityService.testMysqlData(ids);
            long stopMysql = System.currentTimeMillis();

            System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
            System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));
        }
        finally {
            HiberUtil.shutdown();
            RedisUtil.shutdown();
        }
    }
}

