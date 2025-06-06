package com.kerry.netflix.user.application

import com.kerry.netflix.exception.UserException
import com.kerry.netflix.user.application.inp.ReadUser
import com.kerry.netflix.user.application.inp.RegisterUser
import com.kerry.netflix.user.domain.KakaoUserClient
import com.kerry.netflix.user.domain.User
import com.kerry.netflix.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val kakaoUserClient: KakaoUserClient
): ReadUser, RegisterUser {

    override fun findByEmail(email: String): User {
        return userRepository.findUserByEmail(email)
            ?: throw UserException.UserNotFoundException(email)
    }

    override fun getUserFromKakao(token: String): User {
        val user = kakaoUserClient.getUserFromKakao(token)

        if (userRepository.existsByProviderUserId(user.providerUserId!!)) {
            return userRepository.findUserByProviderUserId(user.providerUserId!!)!!
        }

        return userRepository.save(user)
    }

    override fun register(
        email: String,
        nickname: String,
        password: String,
        phone: String?
    ): User {
        if (userRepository.existsByEmail(email)) {
            throw UserException.UserAlreadyExistsException(email)
        }

        val user = User(
            nickname = nickname,
            password = password,
            phone = phone,
            email = email
        )
        return userRepository.save(user)
    }
}