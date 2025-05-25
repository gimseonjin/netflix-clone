package com.kerry.netflix.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing()
@Configuration
class JpaAuditConfig {
}