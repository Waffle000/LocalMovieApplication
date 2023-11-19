package com.waffle.localmovieapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.waffle.core.base.BaseFragment
import com.waffle.core.base.Resource
import com.waffle.localmovieapplication.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by inject()

    private lateinit var homeAdapter : HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        homeAdapter = HomeAdapter(this)
    }

    override fun observeData() {
        viewModel.apply {
            getPopularList().observe(this@HomeFragment) {data ->
                when(data) {
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
                        hideLoading(binding.pbLoading)
                    }
                    is Resource.Loading -> {
                        showLoading(binding.pbLoading)
                    }
                    is Resource.Success -> {
                        homeAdapter.setData(data.data ?: listOf())
                        hideLoading(binding.pbLoading)
                    }
                }

            }
        }
    }

    override fun init() {
        binding.apply {
            rvMoviesByCategory.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            btnFavorite.setOnClickListener{
                startActivity(Intent(requireContext(), Class.forName("com.waffle.favoritefeature.MainActivity")))
            }
        }
    }

}