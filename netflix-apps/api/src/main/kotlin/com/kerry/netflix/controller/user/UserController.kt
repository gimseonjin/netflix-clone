package com.kerry.netflix.controller.user

import com.kerry.netflix.ApiResponse
import com.kerry.netflix.auth.application.inp.CreateAuthAccount
import com.kerry.netflix.auth.application.inp.ReadToken
import com.kerry.netflix.auth.application.inp.UpdateToken
import com.kerry.netflix.auth.domain.Token
import com.kerry.netflix.controller.user.req.UserLoginReq
import com.kerry.netflix.controller.user.req.UserRegisterReq
import com.kerry.netflix.controller.user.res.UserRegisterRes
import com.kerry.netflix.controller.user.res.UserTokenRes
import com.kerry.netflix.user.application.inp.ReadUser
import com.kerry.netflix.user.application.inp.RegisterUser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val registerUser: RegisterUser,
    private val readUser: ReadUser,
    private val readToken: ReadToken,
    private val updateToken: UpdateToken,
    private val createAuthAccount: CreateAuthAccount,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/api/v1/user/register")
    fun register(@RequestBody userRegisterReq: UserRegisterReq ): ApiResponse<UserRegisterRes> {
        val user = registerUser.register(
            nickname = userRegisterReq.nickname,
            password = passwordEncoder.encode(userRegisterReq.password),
            phone = userRegisterReq.phone,
            email = userRegisterReq.email!!
        )
        createAuthAccount.createEmailAuthAccount(
            userId = user.id!!,
            email = user.email!!
        )
        return ApiResponse.ok(UserRegisterRes(
            username = user.nickname,
            phone = user.phone,
            email = user.email
        ))
    }

    @PostMapping("/api/v1/user/login")
    fun login(@RequestBody userLoginReq: UserLoginReq): ApiResponse<UserTokenRes> {
        val user = readUser.findByEmail(userLoginReq.email)

        passwordEncoder
            .matches(userLoginReq.password, user.password)
            .takeIf { it } ?: throw IllegalArgumentException("Invalid username or password")

        val token: Token = updateToken.upsertToken(
            userIdentifier = user.nickname
        )
        return ApiResponse.ok(UserTokenRes(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken
        ))
    }

    @PostMapping("/api/v1/user/callback")
    fun callback(@RequestBody req: Map<String, String>): ApiResponse<UserTokenRes> {
        val code = req["code"] ?: throw IllegalArgumentException("Code is required")

        val accessToken = readToken.getTokenFromKakao(code)
        val user = readUser.getUserFromKakao(accessToken)

        createAuthAccount.createSocialAuthAccount(
            userId = user.id!!,
            provider = user.provider!!,
            providerId = user.providerUserId!!
        )

        val token = updateToken.upsertToken(
            userIdentifier = user.providerUserId!!.toString()
        )
        return ApiResponse.ok(UserTokenRes(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken
        ))
    }
}