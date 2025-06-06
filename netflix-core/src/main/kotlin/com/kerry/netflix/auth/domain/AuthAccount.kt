package com.kerry.netflix.auth.domain

import com.kerry.netflix.base.BaseDomain
import jakarta.persistence.*

@Entity
@Table(name = "auth_account")
class AuthAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false, unique = true)
    var userId: Long
): BaseDomain() {
    @OneToOne(
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    lateinit var credential: AuthCredential
}