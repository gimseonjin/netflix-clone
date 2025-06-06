package com.kerry.netflix.user.domain

interface KakaoUserClient {
    fun getUserFromKakao(token: String): User
}