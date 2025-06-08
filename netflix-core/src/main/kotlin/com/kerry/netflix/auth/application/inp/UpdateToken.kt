package com.kerry.netflix.auth.application.inp

import com.kerry.netflix.auth.domain.Token

interface UpdateToken {

    fun upsertToken(email: String): Token
}