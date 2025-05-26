package com.kerry.netflix.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun save(key: String, value: String) {
        redisTemplate.opsForValue()[key] = value
    }

    fun saveTtl(key: String, value: String, ttl: Long) {
        redisTemplate.opsForValue().set(key, value, ttl)
    }

    fun findByKey(key: String): String? {
        return redisTemplate.opsForValue()[key]
    }

    fun deleteByKey(key: String) {
        redisTemplate.delete(key)
    }
}