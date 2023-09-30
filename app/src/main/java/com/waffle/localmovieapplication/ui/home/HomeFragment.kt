package com.waffle.localmovieapplication.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.waffle.localmovieapplication.databinding.FragmentHomeBinding
import com.waffle.localmovieapplication.module.SharedPreferences
import com.waffle.localmovieapplication.service.Constants
import com.waffle.localmovieapplication.service.NotificationService
import com.waffle.movieappupdate.base.BaseFragment
import com.waffle.localmovieapplication.ui.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import xyz.hasnat.sweettoast.SweetToast


@ExperimentalPagingApi
class HomeFragment : BaseFragment(){

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by inject()

    private lateinit var popularAdapter: PopularAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        val connected =
            connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
                    connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED

        popularAdapter = PopularAdapter(this)
        viewModel.getPopularListLocal()

    }

    override fun observeData() {
        viewModel.apply {
            observeGetPopularSuccess().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    if (data == null) {
                        getPopularList(1, true)
                    } else {
                        binding.apply {
                            rvMoviesByCategory.apply {
                                adapter = MovieAdapter(data, this@HomeFragment)
                                layoutManager = LinearLayoutManager(requireContext())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun init() {
        binding.apply {
            if (SharedPreferences(requireContext()).notification) {
                btnNotifOff.isGone = false
                btnNotifOn.isGone = true
            } else {
                btnNotifOff.isGone = true
                btnNotifOn.isGone = false
            }

            btnNotifOn.setOnClickListener {
                startNotificationService()
                btnNotifOff.isGone = false
                btnNotifOn.isGone = true
            }

            btnNotifOff.setOnClickListener {
                stopNotificationService()
                btnNotifOff.isGone = true
                btnNotifOn.isGone = false
            }
        }
    }

    private fun startNotificationService() {
        val intent = Intent(requireContext(), NotificationService::class.java)
        intent.action = Constants.ACTION_START_NOTIFICATION_SERVICE
        activity?.startService(intent)
        SharedPreferences(requireContext()).notification = true
        SweetToast.success(requireContext(), "Notification service started")

    }

    private fun stopNotificationService() {
        val intent = Intent(activity?.applicationContext, NotificationService::class.java)
        intent.action = Constants.ACTION_STOP_NOTIFICATION_SERVICE
        activity?.startService(intent)
        SharedPreferences(requireContext()).notification = false
        SweetToast.error(requireContext(), "Notification service stopped")
    }

}