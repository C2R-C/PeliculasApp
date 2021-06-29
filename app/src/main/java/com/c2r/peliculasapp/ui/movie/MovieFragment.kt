package com.c2r.peliculasapp.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.c2r.peliculasapp.R
import com.c2r.peliculasapp.core.Resource
import com.c2r.peliculasapp.data.model.Movie
import com.c2r.peliculasapp.data.remote.MovieDataSource
import com.c2r.peliculasapp.databinding.FragmentMovieBinding
import com.c2r.peliculasapp.presentation.MovieViewModel
import com.c2r.peliculasapp.presentation.MovieViewModelFactory
import com.c2r.peliculasapp.repository.MovieRepositoryImpl
import com.c2r.peliculasapp.repository.RetrofitClient
import com.c2r.peliculasapp.ui.movie.adapters.MovieAdapter
import com.c2r.peliculasapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.c2r.peliculasapp.ui.movie.adapters.concat.TopRateConcatAdapter
import com.c2r.peliculasapp.ui.movie.adapters.concat.UpcomingConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webservice)
            )
        )
    }
    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    Log.d("VER", "Loading...")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("VER", "${it.data}")
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    it.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRateConcatAdapter(
                                MovieAdapter(
                                    it.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    it.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }

                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${it.exception}")
                }
            }
        })

    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.title,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}