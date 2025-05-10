package com.kerry.core.user.domain

enum class UserSubscriptionType(val desc: String) {
    FREE("무료 구독권"),
    BRONZE("브론즈 구독권"),
    SILVER("실버 구독권"),
    GOLD("골드 구독권");

    fun toRole(): String = "ROLE_${name}"
}
