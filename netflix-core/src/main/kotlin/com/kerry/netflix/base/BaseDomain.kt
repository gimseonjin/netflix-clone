package com.kerry.netflix.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseDomain(
    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private val createdAt: LocalDateTime? = null,

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private val createdBy: String? = null,

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private var modifiedAt: LocalDateTime? = null,

    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    private var modifiedBy: String? = null,
)