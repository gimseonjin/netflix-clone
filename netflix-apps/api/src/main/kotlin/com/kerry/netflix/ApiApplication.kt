package com.kerry.netflix

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.stereotype.Component

@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    SpringApplication(ApiApplication::class.java).apply {
        addListeners(EnvironmentLogger())
    }.run(*args)
}

class EnvironmentLogger : ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        println("===== PropertySources BEFORE Auto-Configuration =====")
        event.environment.propertySources.forEach { ps ->
            println("• ${ps.name}")
        }
        println("=====================================================")
    }
}