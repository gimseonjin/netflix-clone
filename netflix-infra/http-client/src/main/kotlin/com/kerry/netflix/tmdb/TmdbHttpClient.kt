package com.kerry.netflix.tmdb

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Component
class TmdbHttpClient(
    private val restTemplate: RestTemplate,

    @Value("\${tmdb.auth.access-token}")
    private val accessToken: String
) {

    fun request(
        uri: String,
        httpMethod: HttpMethod,
        headers: MultiValueMap<String, String> = LinkedMultiValueMap(),
        params: Map<String, Any> = emptyMap()
    ): String {
        // 기본 헤더 설정
        val httpHeaders = HttpHeaders().apply {
            accept = listOf(MediaType.APPLICATION_JSON)
            setBearerAuth(accessToken)
            headers.forEach { (key, values) ->
                values.forEach { add(key, it) }
            }
        }

        // 요청 실행
        return restTemplate.exchange(
            uri,
            httpMethod,
            HttpEntity<Any>(httpHeaders),
            object : ParameterizedTypeReference<String>() {},
            params
        ).body
            ?: ""
    }
}