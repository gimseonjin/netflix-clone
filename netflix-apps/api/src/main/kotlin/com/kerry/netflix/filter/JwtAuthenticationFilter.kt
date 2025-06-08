package com.kerry.netflix.filter

import com.kerry.netflix.auth.application.inp.ReadToken
import com.kerry.netflix.security.AuthUser
import com.kerry.netflix.user.application.inp.ReadUser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val readToken: ReadToken,
    private val readUser: ReadUser
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println(request.getHeader("Authorization"))
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")
        if (token != null && readToken.validateToken(token)) {
            val email = readToken.getEmailFromToken(token)
            val user = readUser.findByEmail(email)
            val authorities = listOf("user")
            val auth = updateAuthentication(
                email = user.email,
                userId = user.id!!,
                phone = user.phone,
                password = user.password,
                authorities = authorities
            )
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(request, response)
    }

    private fun updateAuthentication(
        email: String,
        userId: Long,
        phone: String?,
        password: String?,
        authorities: List<String>
    ): UsernamePasswordAuthenticationToken {
        val authUser = AuthUser(
            id = userId,
            username = email,
            password = password,
            phone = phone,
            email = email,
            authorities = authorities.map { SimpleGrantedAuthority(it) }
        )
        return UsernamePasswordAuthenticationToken(authUser, userId, authUser.authorities)
    }
}