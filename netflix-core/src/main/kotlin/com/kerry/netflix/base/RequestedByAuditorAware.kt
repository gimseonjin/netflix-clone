package com.kerry.netflix.base

import org.springframework.context.ApplicationContext
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*


@Component
class RequestedByAuditorAware(
    private val applicationContext: ApplicationContext
) : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        val requestBy = applicationContext
            .getBean(RequestedByProvider::class.java)
            .getRequestedBy() ?: "system"
        return Optional.of(requestBy)
    }
}