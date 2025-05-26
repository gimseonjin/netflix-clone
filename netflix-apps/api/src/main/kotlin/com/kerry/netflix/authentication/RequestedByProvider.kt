package com.kerry.netflix.authentication

interface RequestedByProvider {
    fun getRequestedBy(): String?
}