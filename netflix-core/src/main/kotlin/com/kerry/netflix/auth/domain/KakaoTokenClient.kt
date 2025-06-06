package com.kerry.netflix.auth.domain

interface KakaoTokenClient {

    fun getAccessToken(code: String): String
}