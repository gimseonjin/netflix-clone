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

    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw UserException.UserNotFoundException(username)
    }

    override fun getUserFromKakao(token: String): User {
        val user = kakaoUserClient.getUserFromKakao(token)

        if (userRepository.existsByUsername(user.username)) {
            return userRepository.findByUsername(user.username)!!
        }

        return userRepository.save(user)
    }

    override fun register(
        username: String,
        password: String,
        phone: String?,
        email: String?
    ): User {
        if (userRepository.existsByUsername(username)) {
            throw UserException.UserAlreadyExistsException(username)
        }

        val user = User(
            username = username,
            password = password,
            phone = phone,
            email = email
        )
        return userRepository.save(user)
    }
}