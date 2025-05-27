package com.kerry.netflix.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthUser(
    private val id: Long,
    username: String,
    password: String,
    private val phone: String,
    private val email: String,
    authorities: List<GrantedAuthority>
): User(
    username,
    password,
    authorities
) {
}