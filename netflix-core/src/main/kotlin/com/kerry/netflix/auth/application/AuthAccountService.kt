package com.kerry.netflix.auth.application

import com.kerry.netflix.auth.application.inp.CreateAuthAccount
import com.kerry.netflix.auth.domain.AuthAccount
import com.kerry.netflix.auth.domain.AuthAccountRepository
import com.kerry.netflix.auth.domain.EmailCredential
import com.kerry.netflix.auth.domain.SocialCredential
import org.springframework.stereotype.Service

@Service
class AuthAccountService(
    private val authAccountRepository: AuthAccountRepository
): CreateAuthAccount {
    override fun createEmailAuthAccount(
        userId: Long,
        email: String
    ): AuthAccount {
        if(authAccountRepository.existsByUserId(userId)) {
            return authAccountRepository.findByUserId(userId)!!
        }

        val authAccount = AuthAccount(
            userId = userId
        ).apply {
            this.credential = EmailCredential(
                email = email,
            )
        }
        return authAccountRepository.save(authAccount)
    }

    override fun createSocialAuthAccount(
        userId: Long,
        provider: String,
        providerId: Long
    ): AuthAccount {
        if(authAccountRepository.existsByUserId(userId)) {
            return authAccountRepository.findByUserId(userId)!!
        }

        val authAccount = AuthAccount(
            userId = userId
        ).apply {
            this.credential = SocialCredential(
                provider = provider,
                providerUserId = providerId
            )
        }
        return authAccountRepository.save(authAccount)
    }
}