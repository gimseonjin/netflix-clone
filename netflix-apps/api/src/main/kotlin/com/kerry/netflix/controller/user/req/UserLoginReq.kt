package com.kerry.netflix.controller.user.req

data class UserLoginReq(
    val email: String,
    val password: String
) {
}