package com.kerry.netflix.auth.application

import com.kerry.netflix.auth.application.inp.ReadToken
import com.kerry.netflix.auth.application.inp.UpdateToken
import com.kerry.netflix.auth.domain.KakaoTokenClient
import com.kerry.netflix.auth.domain.Token
import com.kerry.netflix.auth.domain.TokenRepository
import org.springframework.stereotype.Service
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date.from
import javax.crypto.SecretKey

@Service
class TokenService(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    private val tokenRepository: TokenRepository,
    private val tokenClient: KakaoTokenClient
) : ReadToken, UpdateToken {
    override fun upsertToken(email: String): Token {
        val existingToken = tokenRepository.findByUserId(email)
        val now = Instant.now()

        val accessDuration = Duration.ofMinutes(30)
        val refreshDuration = Duration.ofDays(7)

        val accessToken = getToken(
            email = email,
            now = now,
            expireAt = accessDuration
        )
        val refreshToken = getToken(
            email = email,
            now = now,
            expireAt = refreshDuration
        )
        val accessTokenExpiresAt = LocalDateTime.ofInstant(now.plus(accessDuration), ZoneId.systemDefault())
        val refreshTokenExpiresAt = LocalDateTime.ofInstant(now.plus(refreshDuration), ZoneId.systemDefault())

        return if (existingToken != null) {
            existingToken.accessToken = accessToken
            existingToken.refreshToken = refreshToken
            existingToken.accessTokenExpiresAt = accessTokenExpiresAt
            existingToken.refreshTokenExpiresAt = refreshTokenExpiresAt
            tokenRepository.save(existingToken)
        } else {
            val newToken = Token(
                userId = email,
                accessToken = accessToken,
                refreshToken = refreshToken,
                accessTokenExpiresAt = accessTokenExpiresAt,
                refreshTokenExpiresAt = refreshTokenExpiresAt
            )
            tokenRepository.save(newToken)
        }
    }

    override fun validateToken(token: String): Boolean {
        Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)

        return true
    }

    override fun getTokenFromKakao(code: String): String {
        return tokenClient.getAccessToken(code)
    }

    override fun getEmailFromToken(token: String): String {
        val claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload

        return (claims["email"] as? String)
            ?: throw IllegalArgumentException("토큰에 email 클레임이 없습니다.")
    }

    private fun getToken(
        email: String,
        now: Instant,
        expireAt: Duration
    ): String {
        return Jwts.builder()
            .issuedAt(from(now))
            .claim("email", email)
            .expiration(from(now.plus(expireAt)))
            .signWith(getSigningKey())
            .compact()
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}