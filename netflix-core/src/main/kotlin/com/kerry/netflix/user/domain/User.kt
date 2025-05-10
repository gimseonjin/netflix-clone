package com.kerry.netflix.user.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(name = "USER_NAME", nullable = false, unique = true)
    var username: String,

    @Column(name = "PASSWORD", nullable = false)
    var password: String,

    @Column(name = "EMAIL", nullable = false)
    var email: String,

    @Column(name = "PHONE", nullable = false)
    var phone: String,
) {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val histories: MutableList<UserHistory> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val downloads: MutableList<UserMovieDownload> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<UserMovieLike> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val subscriptions: MutableList<UserSubscription> = mutableListOf()
}