package com.kerry.netflix.controller.user.res

data class UserTokenRes(
    val accessToken: String,
    val refreshToken: String
) {
}