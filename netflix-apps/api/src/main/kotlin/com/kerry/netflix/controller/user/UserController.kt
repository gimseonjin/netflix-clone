package com.kerry.netflix.controller.user

import com.kerry.netflix.ApiResponse
import com.kerry.netflix.auth.application.inp.ReadToken
import com.kerry.netflix.controller.user.req.UserLoginReq
import com.kerry.netflix.controller.user.req.UserRegisterReq
import com.kerry.netflix.controller.user.res.UserRegisterRes
import com.kerry.netflix.user.application.inp.ReadUser
import com.kerry.netflix.user.application.inp.RegisterUser
import com.kerry.netflix.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val registerUser: RegisterUser,
    private val readUser: ReadUser,
    private val readToken: ReadToken,
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

    @PostMapping("/api/v1/user/login")
    fun login(@RequestBody userLoginReq: UserLoginReq): ApiResponse<String> {
        // Implement login logic here
        // This is a placeholder for the actual login implementation
        return ApiResponse.ok("access")
    }

    @PostMapping("/api/v1/user/callback")
    fun callback(@RequestBody req: Map<String, String>): ApiResponse<User> {
        val code = req["code"] ?: throw IllegalArgumentException("Code is required")

        val accessToken = readToken.getTokenFromKakao(code)

        val user = readUser.getUserFromKakao(accessToken)

        return ApiResponse.ok(user)
    }
}