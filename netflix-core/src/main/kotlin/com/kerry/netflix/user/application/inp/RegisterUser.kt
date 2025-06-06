package com.kerry.netflix.user.application.inp

import com.kerry.netflix.user.domain.User

interface RegisterUser {
    fun register(username: String, password: String, phone: String? = null, email: String? = null): User
}