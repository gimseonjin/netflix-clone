package com.kerry.netflix.user.domain

import com.kerry.netflix.base.BaseDomain
import jakarta.persistence.*

@Entity
@Table(name = "user_histories")
class UserHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "USER_ROLE", nullable = false)
    var userRole: String,

    @Column(name = "REQ_IP", nullable = false)
    var clientIp: String,

    @Column(name = "REQ_METHOD", nullable = false)
    var reqMethod: String,

    @Column(name = "REQ_URL", nullable = false)
    var reqUrl: String,

    @Column(name = "REQ_HEADER", columnDefinition = "TEXT", nullable = false)
    var reqHeader: String,

    @Column(name = "REQ_PAYLOAD", columnDefinition = "TEXT", nullable = false)
    var reqPayload: String
): BaseDomain() {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    lateinit var user: User
}