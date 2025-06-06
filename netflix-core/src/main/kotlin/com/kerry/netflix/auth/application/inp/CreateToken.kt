package com.kerry.netflix.auth.application.inp

import com.kerry.netflix.auth.domain.Token

interface CreateToken {
    fun createNewToken(username: String, password: String): Token
}