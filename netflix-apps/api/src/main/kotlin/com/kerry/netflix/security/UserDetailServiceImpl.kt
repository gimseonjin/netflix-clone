package com.kerry.netflix.security


import com.kerry.netflix.user.application.inp.ReadUser
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val readUser: ReadUser
): UserDetailsService {
    override fun loadUserByUsername(email: String): AuthUser {
        val user = readUser.findByEmail(email)
        return AuthUser(
            id = user.id!!,
            username = user.email ?: user.providerUserId!!.toString(),
            password = user.password,
            phone = user.phone,
            email = user.email,
            authorities = listOf()
        )
    }
}