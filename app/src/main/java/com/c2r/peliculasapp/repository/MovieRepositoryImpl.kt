package com.c2r.peliculasapp.repository

import com.c2r.peliculasapp.core.InternetCheck
import com.c2r.peliculasapp.data.local.LocalMovieDataSource
import com.c2r.peliculasapp.data.model.MovieList
import com.c2r.peliculasapp.data.remote.RemoteMovieDataSource
import com.c2r.peliculasapp.utils.toMovieEntity

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getUpcomingMovies().results.forEach {
                dataSourceLocal.saveMovie(it.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getTopRatedMovies().results.forEach {
                dataSourceLocal.saveMovie(it.toMovieEntity("toprated"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }

    }

    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getPopularMovies().results.forEach {
                dataSourceLocal.saveMovie(it.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }

    }
}