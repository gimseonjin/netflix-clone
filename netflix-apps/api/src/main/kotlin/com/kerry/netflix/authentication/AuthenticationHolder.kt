package com.kerry.netflix.authentication

interface AuthenticationHolder {
    fun getAuthentication(): Authentication?
    fun setAuthentication(authentication: Authentication)
}