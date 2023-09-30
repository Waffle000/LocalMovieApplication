package com.waffle.localmovieapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.waffle.localmovieapplication.R
import com.waffle.localmovieapplication.databinding.FragmentHomeBinding
import com.waffle.localmovieapplication.module.SharedPreferences
import com.waffle.localmovieapplication.service.Constants
import com.waffle.localmovieapplication.service.NotificationService
import com.waffle.localmovieapplication.ui.MainViewModel
import com.waffle.movieappupdate.base.BaseFragment
import org.koin.android.ext.android.inject
import xyz.hasnat.sweettoast.SweetToast


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.getPopularListLocal(true)
    }

    override fun observeData() {
        viewModel.apply {
            observeGetPopularSuccess().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    if (data.first.isEmpty()) {
                        getPopularList(1, true)
                    } else {
                        binding.apply {
                            if(data.second) {
                                rvMoviesByCategory.apply {
                                    adapter = HomeAdapter(data.first, this@HomeFragment)
                                    layoutManager = LinearLayoutManager(requireContext())
                                }
                            } else {
                                showNotification()
                                btnShowNewData.setOnClickListener {
                                    rvMoviesByCategory.apply {
                                        adapter = HomeAdapter(data.first, this@HomeFragment)
                                        layoutManager = LinearLayoutManager(requireContext())
                                    }
                                    closeNotification()
                                }
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

    private fun closeNotification() {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.setDuration(600)
        transition.addTarget(R.id.parent_notif)
        TransitionManager.beginDelayedTransition(binding.root, transition)
        binding.parentNotif.isGone = true
    }
    private fun showNotification() {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.setDuration(600)
        transition.addTarget(R.id.parent_notif)
        TransitionManager.beginDelayedTransition(binding.root, transition)
        binding.parentNotif.isGone = false
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