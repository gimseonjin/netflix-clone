package com.kerry.netflix.security


import com.kerry.netflix.user.application.inp.ReadUser
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val readUser: ReadUser
): UserDetailsService {
    override fun loadUserByUsername(username: String): AuthUser {
        val user = readUser.findByUsername(username)
        return AuthUser(
            id = user.id!!,
            username = user.username,
            password = user.password,
            phone = user.phone,
            email = user.email,
            authorities = listOf()
        )
    }
}