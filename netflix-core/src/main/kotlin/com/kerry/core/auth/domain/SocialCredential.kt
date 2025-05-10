package com.kerry.core.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("SOCIAL")
class SocialCredential(

    @Column(name = "provider", nullable = false)
    var provider: String = "",

    @Column(name = "provider_user_id", nullable = false)
    var providerUserId: String = "",

    override val type: String = "SOCIAL"
) : AuthCredential()