package com.kerry.netflix.auth.domain

import jakarta.persistence.*

@Entity
@Table(name = "auth_credential")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
abstract class AuthCredential(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    var account: AuthAccount? = null

    abstract val type: String
}