package com.kerry.netflix

import com.kerry.netflix.base.RequestedByProvider
import org.springframework.stereotype.Component

@Component
class BatchRequestedByProviderImpl: RequestedByProvider {
    override fun getRequestedBy(): String {
        return "batch"
    }
}