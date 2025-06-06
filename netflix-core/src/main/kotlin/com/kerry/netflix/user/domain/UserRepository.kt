package com.kerry.netflix.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findUserByEmail(email: String): User?
    fun findUserByProviderUserId(providerUserId: Long): User?
    fun existsByEmail(email: String): Boolean
    fun existsByProviderUserId(providerUserId: Long): Boolean
}