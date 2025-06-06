package com.kerry.netflix.auth.application.inp

import com.kerry.netflix.auth.domain.AuthAccount

interface CreateAuthAccount {

    fun createEmailAuthAccount(userId: Long,  email: String): AuthAccount
    fun createSocialAuthAccount(userId: Long, provider: String, providerId: Long): AuthAccount
}