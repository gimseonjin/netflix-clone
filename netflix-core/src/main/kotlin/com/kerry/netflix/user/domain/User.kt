package com.kerry.netflix.user.domain

import com.kerry.netflix.base.BaseDomain
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "NICK_NAME", nullable = false, unique = true)
    var nickname: String,

    @Column(name = "PASSWORD", nullable = true)
    var password: String? = null,

    @Column(name = "EMAIL", nullable = false, unique = true)
    var email: String,

    @Column(name = "PHONE", nullable = true)
    var phone: String? = null,

    @Column(name = "PROVIDER", nullable = true)
    val provider: String? = null,

    @Column(name = "PROVIDER_USER_ID", nullable = true)
    val providerUserId: Long? = null,
): BaseDomain() {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val histories: MutableList<UserHistory> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val downloads: MutableList<UserMovieDownload> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<UserMovieLike> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val subscriptions: MutableList<UserSubscription> = mutableListOf()
}