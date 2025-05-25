package com.kerry.netflix.movie.domain

import com.kerry.netflix.base.BaseDomain
import jakarta.persistence.*

@Entity
@Table(name = "movies")
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "MOVIE_NAME")
    var title: String,

    @Column(name = "IS_ADULT")
    var isAdult: Boolean,

    @Column(name = "GENRE")
    @Enumerated(EnumType.STRING)
    var genre: MovieGenre,

    @Column(name = "OVERVIEW", columnDefinition = "TEXT")
    var overview: String,

    @Column(name = "RELEASED_AT")
    var releasedAt: String
): BaseDomain()