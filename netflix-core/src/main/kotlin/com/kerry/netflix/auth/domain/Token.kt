package com.kerry.netflix.auth.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tokens")
class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID")
    var tokenId: Long? = null,

    @Column(name = "USER_ID", nullable = false)
    var userId: Long,

    @Column(name = "ACCESS_TOKEN", nullable = false)
    var accessToken: String,

    @Column(name = "REFRESH_TOKEN", nullable = false)
    var refreshToken: String,

    @Column(name = "ACCESS_TOKEN_EXPIRES_AT", nullable = false)
    var accessTokenExpiresAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "REFRESH_TOKEN_EXPIRES_AT", nullable = false)
    var refreshTokenExpiresAt: LocalDateTime = LocalDateTime.now()
)