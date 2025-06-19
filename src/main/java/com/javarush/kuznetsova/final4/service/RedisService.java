package com.javarush.kuznetsova.final4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kuznetsova.final4.redis.CityCountry;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RedisService {
    private final RedisClient redisClient;
    private final ObjectMapper mapper;

    public RedisService(RedisClient redisClient, ObjectMapper mapper) {
        this.redisClient = redisClient;
        this.mapper = mapper;
    }
    public void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    log.error("Ошибка записи в Redis", e);
                }
            }
        }
    }
    public void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    mapper.readValue(value, CityCountry.class);
                } catch (JsonProcessingException e) {
                    log.error("Ошибка чтения из Redis", e);
                }
            }
        }
    }
}
