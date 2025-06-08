package com.kerry.netflix.auth.application.inp

interface ReadToken {
    fun validateToken(token: String): Boolean
    fun getTokenFromKakao(code: String): String
    fun getEmailFromToken(token: String): String
}