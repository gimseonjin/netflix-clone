package com.kerry.netflix.advice

import com.kerry.netflix.ApiResponse
import com.kerry.netflix.exception.ErrorCode
import com.kerry.netflix.exception.UserException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionAdvice::class.java)

    @ExceptionHandler(UserException::class)
    fun handleUserException(exception: UserException): ApiResponse<Nothing> {
        logger.error("UserException occurred: ${exception.message}", exception)
        return ApiResponse.error(
            code = exception.errorCode,
            message = exception.message
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ApiResponse<Nothing> {
        logger.error("RuntimeException occurred: ${exception.message}", exception)
        return ApiResponse.error(
            code = ErrorCode.DEFAULT_ERROR,
            message = "An unexpected error occurred."
        )
    }
}