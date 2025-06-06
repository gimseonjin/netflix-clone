package com.kerry.netflix.config

import com.kerry.netflix.security.UserDetailServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailService: UserDetailServiceImpl
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf { it.disable() }
        httpSecurity.httpBasic { it.disable() }
        httpSecurity.formLogin { it.disable() }
        httpSecurity.cors { it.configurationSource(corsConfigurationSource()) }

        httpSecurity.userDetailsService(userDetailService)

        httpSecurity.authorizeHttpRequests {
            it.requestMatchers("/api/v1/user/register").permitAll()
            it.anyRequest().authenticated()
        }

//        httpSecurity.oauth2Login{ it.failureUrl("/login?error=true") }

        return httpSecurity.build()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOriginPatterns = listOf("*")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}