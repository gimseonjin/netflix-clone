package com.kerry.netflix.controller.user.res

data class UserRegisterRes(
    val username: String,
    val phone: String? = null,
    val email: String? = null
)
