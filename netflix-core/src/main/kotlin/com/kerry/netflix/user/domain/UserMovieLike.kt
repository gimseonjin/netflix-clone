package com.kerry.netflix.user.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_movie_likes")
class UserMovieLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "USER_ID")
    var userId: Long,

    @Column(name = "MOVIE_ID")
    var movieId: Long,

    @Column(name = "LIKE_YN")
    var likeYn: Boolean
) {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    lateinit var user: User
}