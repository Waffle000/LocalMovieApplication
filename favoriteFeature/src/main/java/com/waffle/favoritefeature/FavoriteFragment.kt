package com.waffle.favoritefeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.waffle.core.base.BaseFragment
import com.waffle.core.base.Resource
import com.waffle.favoritefeature.databinding.FragmentFavoriteBinding
import com.waffle.favoritefeature.module.favoriteViewModelModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class FavoriteFragment : BaseFragment() {

    private lateinit var binding : FragmentFavoriteBinding

    private val viewModel : FavoriteViewModel by inject()

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        favoriteAdapter = FavoriteAdapter(this)
    }

    override fun observeData() {
        viewModel.apply {
            getPopularFavorite().observe(this@FavoriteFragment) { data ->
                when(data) {
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
                        hideLoading(binding.pbLoading)
                    }
                    is Resource.Loading -> {
                        showLoading(binding.pbLoading)
                    }
                    is Resource.Success -> {
                        favoriteAdapter.setData(data.data ?: listOf())
                        hideLoading(binding.pbLoading)
                    }
                }
            }
        }
    }

    override fun init() {
        binding.apply {
            btnBack.setOnClickListener { activity?.finish() }
            rvFavorite.apply {
                adapter = favoriteAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}