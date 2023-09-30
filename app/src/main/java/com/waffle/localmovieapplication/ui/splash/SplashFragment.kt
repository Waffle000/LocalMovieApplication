package com.waffle.localmovieapplication.ui.splash

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import com.waffle.localmovieapplication.R
import com.waffle.localmovieapplication.databinding.FragmentSplashBinding
import com.waffle.localmovieapplication.ui.MainViewModel
import com.waffle.movieappupdate.base.BaseFragment
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment() {

    private lateinit var binding : FragmentSplashBinding

    private val viewModel : MainViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.getPopularListLocal(true)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                else ->     false
            }
        }
        else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }
    override fun observeData() {
        viewModel.apply {
            observeGetPopularSuccess().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    if(data.first.isEmpty() && !isNetworkAvailable(requireContext())) {
                        activity?.let { it1 ->
                            AestheticDialog.Builder(it1, DialogStyle.TOASTER, DialogType.ERROR)
                                .setTitle("Gagal Masuk")
                                .setMessage("Data Lokal Kosong dan Internet Tidak Tersambung")
                                .setDarkMode(true)
                                .setGravity(Gravity.BOTTOM)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        getPopularListLocal(true)
                                        dialog.dismiss()
                                    }
                                })
                                .show()
                        }
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                        }, 1000)
                    }
                }
            }
        }
    }

    override fun init() {
    }
}