package com.kerry.netflix.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val objectMapper = jacksonObjectMapper()

inline fun <reified T> String.toObject(): T =
    try {
        objectMapper.readValue(this)
    } catch (e: JsonProcessingException) {
        throw IllegalArgumentException("Failed to parse JSON to ${T::class.java.simpleName}", e)
    }

fun Any.toJson(): String =
    try {
        objectMapper.writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        throw IllegalStateException("Failed to convert object to JSON", e)
    }