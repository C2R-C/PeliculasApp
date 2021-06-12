package com.c2r.peliculasapp.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.c2r.peliculasapp.R
import com.c2r.peliculasapp.application.AppConstants
import com.c2r.peliculasapp.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)

        Glide.with(requireContext())
            .load("${AppConstants.BASE_URL_IMAGE}${args.posterImageUrl}")
            .centerCrop()
            .into(binding.imgMovie)
        Glide.with(requireContext())
            .load("${AppConstants.BASE_URL_IMAGE}${args.backgroundImageUrl}")
            .centerCrop()
            .into(binding.imgBackground)
        binding.txtMovieTitle.text = args.title
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtReleased.text = "Released ${args.releaseData}"
        binding.txtDescription.text = args.overview
    }
}