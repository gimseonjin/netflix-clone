package com.kerry.netflix.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("EMAIL")
class EmailCredential(
    @Column(name = "email", nullable = true, unique = true)
    var email: String? = null,

    @Column(name = "password_hash", nullable = true)
    var passwordHash: String? = null,

) : AuthCredential()