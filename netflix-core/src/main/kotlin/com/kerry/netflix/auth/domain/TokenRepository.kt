package com.kerry.netflix.auth.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository: JpaRepository<Token, Long> {
}