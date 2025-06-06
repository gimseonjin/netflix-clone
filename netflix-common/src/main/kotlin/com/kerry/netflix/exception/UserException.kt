package com.kerry.netflix.exception

sealed class UserException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException(message) {

    class UserNotFoundException(
        username: String
    ) : UserException(
        ErrorCode.USER_NOT_FOUND,
        "사용자 이름 '$username'을(를) 찾을 수 없습니다."
    )
}