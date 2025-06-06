package com.kerry.netflix.user.application.inp

import com.kerry.netflix.user.domain.User

interface RegisterUser {
    fun register( email: String, nickname: String, password: String, phone: String? = null): User
}