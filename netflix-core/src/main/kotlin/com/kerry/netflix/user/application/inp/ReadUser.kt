package com.kerry.netflix.user.application.inp

import com.kerry.netflix.user.domain.User

interface ReadUser {

    fun findByUsername(username: String): User
}