package com.waffle.core.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.waffle.core.utils.disableScreen
import com.waffle.core.utils.enableScreen

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(savedInstanceState)
        observeData()
        init()
    }

    protected abstract fun onViewCreated(savedInstanceState: Bundle?)

    protected abstract fun observeData()

    protected abstract fun init()

    fun showLoading(loading: ProgressBar){
        activity?.disableScreen()
        loading.visibility = View.VISIBLE
    }

    fun hideLoading(loading: ProgressBar){
        activity?.enableScreen()
        loading.visibility = View.GONE
    }
}