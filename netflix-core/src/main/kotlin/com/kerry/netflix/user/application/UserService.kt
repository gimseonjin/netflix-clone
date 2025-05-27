package com.kerry.netflix.user.application

import com.kerry.netflix.user.application.inp.ReadUser
import com.kerry.netflix.user.domain.User
import com.kerry.netflix.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
): ReadUser {

    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User with username $username not found")
    }
}