package com.kerry.core.user.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_movie_downloads")
class UserMovieDownload(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "USER_ID")
    var userId: Long,

    @Column(name = "MOVIE_ID")
    var movieId: Long
) {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    lateinit var user: User
}