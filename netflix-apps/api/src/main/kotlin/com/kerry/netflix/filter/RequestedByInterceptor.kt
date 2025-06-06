package com.kerry.netflix.filter

import com.kerry.netflix.authentication.AuthenticationHolder
import com.kerry.netflix.authentication.RequestedBy
import org.springframework.stereotype.Component
import org.springframework.ui.ModelMap
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.WebRequestInterceptor


@Component
class RequestedByInterceptor(
     private val authenticationHolder: AuthenticationHolder
): WebRequestInterceptor {
    val REQUESTED_BY_HEADER: String = "requested-by"

    override fun preHandle(request: WebRequest) {
        val requestedBy = request.getHeader(REQUESTED_BY_HEADER) ?: "anonymous"

        val requestUser = RequestedBy(requestedBy)

        authenticationHolder.setAuthentication(requestUser)
    }

    override fun postHandle(request: WebRequest, model: ModelMap?) {
        // No post-processing needed
    }

    override fun afterCompletion(request: WebRequest, ex: Exception?) {
        authenticationHolder.clearAuthentication()
    }
}