package com.kerry.netflix.movie.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository: JpaRepository<Movie, Long> {
    fun findAllBy(pageable: Pageable): Page<Movie>
}