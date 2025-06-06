package com.kerry.netflix.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("SOCIAL")
class SocialCredential(

    @Column(name = "provider", nullable = true)
    var provider: String? = null,

    @Column(name = "provider_user_id", nullable = true)
    var providerUserId: Long? = null,

) : AuthCredential()