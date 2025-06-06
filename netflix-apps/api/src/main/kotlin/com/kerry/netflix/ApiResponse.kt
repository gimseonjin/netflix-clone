package com.kerry.netflix

import com.kerry.netflix.exception.ErrorCode

data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String?,
    val data: T?
) {
    companion object{
        private val CODE_SUCCEED: String = "SUCCEED"

        fun <T> ok(data: T, message: String? = null): ApiResponse<T> {
            return ApiResponse(true, CODE_SUCCEED, message, data)
        }

        fun <T> error(code: ErrorCode, message: String): ApiResponse<T> {
            return ApiResponse(false, code.code, message, null)
        }
    }
}