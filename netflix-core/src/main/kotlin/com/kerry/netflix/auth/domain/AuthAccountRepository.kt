package com.kerry.netflix.auth.domain

import org.springframework.data.jpa.repository.JpaRepository

interface AuthAccountRepository: JpaRepository<AuthAccount, Long> {
    fun existsByUserId(userId: Long): Boolean

    fun findByUserId(userId: Long): AuthAccount?
}