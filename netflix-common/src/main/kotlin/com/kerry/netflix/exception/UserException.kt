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

    class UserAlreadyExistsException(
        username: String
    ) : UserException(
        ErrorCode.USER_ALREADY_EXISTS,
        "사용자 이름 '$username'은(는) 이미 존재합니다."
    )
}