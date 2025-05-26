package com.kerry.netflix.authentication

class RequestedBy(
    private val requestBy: String,
): Authentication {
    override fun getRequestedBy(): String = requestBy
}