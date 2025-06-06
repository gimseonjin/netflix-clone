package com.kerry.netflix.user.application.inp

import com.kerry.netflix.user.domain.User

interface ReadUser {

    fun findByEmail(email: String): User

    fun getUserFromKakao(token: String): User
}