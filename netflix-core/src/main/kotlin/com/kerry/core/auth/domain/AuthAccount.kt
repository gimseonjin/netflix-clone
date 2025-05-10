package com.kerry.core.auth.domain

import jakarta.persistence.*

@Entity
@Table(name = "auth_account")
class AuthAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false, unique = true)
    var userId: Long
) {
    @OneToOne(
        mappedBy = "account",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    lateinit var credential: AuthCredential
}