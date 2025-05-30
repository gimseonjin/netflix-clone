package com.kerry.netflix.user.domain

import com.kerry.netflix.base.BaseDomain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_subscriptions")
class UserSubscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "SUBSCRIPTION_NAME", nullable = false)
    val subscriptionName: UserSubscriptionType,

    @Column(name = "START_AT", nullable = false)
    val subscriptionStartAt: LocalDateTime,

    @Column(name = "END_AT", nullable = false)
    val subscriptionEndAt: LocalDateTime,

    @Column(name = "VALID_YN", nullable = false)
    val validYn: Boolean
): BaseDomain() {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    lateinit var user: User
}