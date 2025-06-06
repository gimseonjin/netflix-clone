package com.kerry.netflix.exception

enum class ErrorCode(
    val code: String,
    val message: String
) {

    DEFAULT_ERROR(
        code = "0000",
        message = "An unexpected error occurred."
    ),

    USER_NOT_FOUND(
        code = "1001",
        message = "User not found."
    ),
}