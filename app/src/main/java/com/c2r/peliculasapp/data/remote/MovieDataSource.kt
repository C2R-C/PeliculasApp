package com.c2r.peliculasapp.data.remote

import com.c2r.peliculasapp.application.AppConstants
import com.c2r.peliculasapp.data.model.MovieList
import com.c2r.peliculasapp.repository.WebService

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)

}