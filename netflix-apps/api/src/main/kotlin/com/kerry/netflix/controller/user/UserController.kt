package com.kerry.netflix.controller.user

import com.kerry.netflix.ApiResponse
import com.kerry.netflix.controller.user.req.UserRegisterReq
import com.kerry.netflix.controller.user.res.UserRegisterRes
import com.kerry.netflix.user.application.inp.RegisterUser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val registerUser: RegisterUser,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/api/v1/user/register")
    fun register(@RequestBody userRegisterReq: UserRegisterReq ): ApiResponse<UserRegisterRes> {
        val user = registerUser.register(
            username = userRegisterReq.username,
            password = passwordEncoder.encode(userRegisterReq.password),
            phone = userRegisterReq.phone,
            email = userRegisterReq.email
        )
        val response = UserRegisterRes(
            username = user.username,
            phone = user.phone,
            email = user.email
        )
        return ApiResponse.ok(response)
    }
}