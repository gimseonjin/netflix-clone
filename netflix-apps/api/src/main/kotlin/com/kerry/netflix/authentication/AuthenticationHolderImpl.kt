package com.kerry.netflix.authentication

import com.kerry.netflix.base.RequestedByProvider
import org.springframework.stereotype.Component

@Component
class AuthenticationHolderImpl : AuthenticationHolder, RequestedByProvider {
    private var authentication: Authentication? = null

    override fun getAuthentication(): Authentication? = authentication

    override fun setAuthentication(authentication: Authentication) {
        this.authentication = authentication
    }

    override fun getRequestedBy(): String? {
        return authentication?.getRequestedBy()
    }

    override fun clearAuthentication() {
        authentication = null
    }
}