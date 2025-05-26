package com.kerry.netflix.config

import com.kerry.netflix.filter.RequestedByInterceptor
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Component
class RequestByMvcConfig(
    private val requestedByInterceptor: RequestedByInterceptor
): WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addWebRequestInterceptor(requestedByInterceptor)
    }
}