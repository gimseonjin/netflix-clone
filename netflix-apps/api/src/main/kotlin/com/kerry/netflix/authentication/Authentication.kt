package com.kerry.netflix.authentication

interface Authentication {
    fun getRequestedBy(): String
}