package com.waffle.localmovieapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.waffle.localmovieapplication.databinding.ActivityMainBinding
import com.waffle.localmovieapplication.ui.module.homeViewModelModule
import org.koin.core.context.loadKoinModules

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(homeViewModelModule)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}