package com.waffle.localmovieapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.waffle.localmovieapplication.databinding.FragmentDiscoverDetailBinding
import com.waffle.localmovieapplication.local.entity.PopularEntity
import com.waffle.movieappupdate.base.BaseFragment
import com.waffle.localmovieapplication.utils.loadImage

class HomeDetailFragment() : BaseFragment() {

    private lateinit var binding: FragmentDiscoverDetailBinding

    private val data by lazy {
        arguments?.getParcelable(DATA) ?: PopularEntity(0,null,null,null,null,null,null, null)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
    }

    override fun observeData() {
    }

    override fun init() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvMovieTitle.text = data.name
            tvMovieYearAndDuration.text = data.date
            tvMovieGenre.text = data.name
            tvMovieRating.text = data.star.toString()
            ivBackdrop.loadImage(data.backdropPath)
            ivThumbnail.loadImage(data.posterPath)
        }
    }
    companion object {
        const val DATA = "DiscoverDetailFragment.data"
    }
}