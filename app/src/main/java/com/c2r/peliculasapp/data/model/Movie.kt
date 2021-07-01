package com.c2r.peliculasapp.data.model

data class Movie(
    val id: Int = -1,
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = -1.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = -1.0,
    val vote_count: Int = -1,
    val movie_type: String = ""

)