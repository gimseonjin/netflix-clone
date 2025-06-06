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
abstract class BaseDomain {

    // 클래스 바디 안에서 프로퍼티 선언 → protected set을 사용할 수 있음
    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    var createdAt: LocalDateTime? = null
        protected set

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    var createdBy: String? = null
        protected set

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    var modifiedAt: LocalDateTime? = null
        protected set

    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    var modifiedBy: String? = null
        protected set
}