package com.javarush.kuznetsova.final4.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RedisUtil {
    private static final RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));

    public static RedisClient getRedisClient() {
        return redisClient;
    }

    public static void shutdown() {
        redisClient.shutdown();
    }
}
