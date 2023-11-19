package com.waffle.favoritefeature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.waffle.favoritefeature.databinding.ActivityMainBinding
import com.waffle.favoritefeature.module.favoriteViewModelModule
import org.koin.core.context.loadKoinModules

class MainActivity : FragmentActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteViewModelModule)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}