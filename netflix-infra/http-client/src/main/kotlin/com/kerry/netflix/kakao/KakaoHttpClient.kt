package com.kerry.netflix.kakao

import com.kerry.netflix.auth.domain.KakaoTokenClient
import com.kerry.netflix.user.domain.KakaoUserClient
import com.kerry.netflix.user.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class KakaoHttpClient(
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val kakaoClientId: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}")
    private val kakaoClientSecret: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val kakaoRedirectUri: String,

    private val restTemplate: RestTemplate
): KakaoTokenClient, KakaoUserClient {

    private val KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    private val KAKAO_USERINFO_API_URL = "https://kapi.kakao.com/v2/user/me"

    override fun getAccessToken(code: String): String {
        val url = "$KAKAO_TOKEN_URL?grant_type=authorization_code&client_id=$kakaoClientId&client_secret=$kakaoClientSecret&redirect_uri=$kakaoRedirectUri&code=$code"

        val response = restTemplate.postForObject(url, null, Map::class.java)
        return response?.get("access_token") as? String ?: throw IllegalStateException("Failed to retrieve access token")
    }

    override fun getUserFromKakao(token: String): User {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(token)
        }
        val httpEntity = HttpEntity<Unit>(headers)

        // 2) exchange를 사용해서 GET 요청 + 헤더 포함
        val responseEntity: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            KAKAO_USERINFO_API_URL,
            HttpMethod.GET,
            httpEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        val response = responseEntity.body
            ?: throw IllegalStateException("카카오 사용자 정보 응답이 비어있습니다.")

        // 차후 auth 쪽에 저장
        val id = (response["id"] as? Number)?.toLong()
            ?: throw IllegalStateException("카카오 응답에 id가 없습니다.")
        val username = (response["properties"] as? Map<*, *>)
            ?.get("nickname")
                as? String
            ?: throw IllegalStateException("카카오 응답에 properties.nickname이 없습니다.")
        val email = (response["kakao_account"] as? Map<*, *>)
            ?.get("email")
                as? String
            ?: ""

        return User(
            nickname = username,
            email = email,
            providerUserId = id,
            provider = "kakao"
        )
    }
}