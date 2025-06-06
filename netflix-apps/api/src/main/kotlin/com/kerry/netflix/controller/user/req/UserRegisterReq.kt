package com.kerry.netflix.controller.user.req

data class UserRegisterReq(
    val username: String,
    val password: String,
    val phone: String? = null,
    val email: String? = null
)
