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
    override fun upsertToken(userIdentifier: String): Token {
        val existingToken = tokenRepository.findByUserId(userIdentifier)
        val now = Instant.now()

        val accessDuration = Duration.ofMinutes(30)
        val refreshDuration = Duration.ofDays(7)

        val accessToken = getTokenByUsername(
            username = userIdentifier,
            now = now,
            expireAt = accessDuration
        )
        val refreshToken = getTokenByUsername(
            username = userIdentifier,
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
                userId = userIdentifier,
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

    override fun getUsernameFromToken(token: String): String {
        val claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload

        return (claims["username"] as? String)
            ?: throw IllegalArgumentException("토큰에 username 클레임이 없습니다.")
    }

    private fun getTokenByUsername(
        username: String,
        now: Instant,
        expireAt: Duration
    ): String {
        return Jwts.builder()
            .issuedAt(from(now))
            .claim("username", username)
            .expiration(from(now.plus(expireAt)))
            .signWith(getSigningKey())
            .compact()
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}