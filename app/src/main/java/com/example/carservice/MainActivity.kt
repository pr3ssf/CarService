package com.example.carservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.carservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    openFragment(HomeFragment())
                    true
                }
                R.id.nav_favourites -> {
                    openFragment(FavouritesFragment())
                    true
                }
                R.id.nav_settings -> {
                    openFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // получаем флаг из Intent
        val openSettings = intent.getBooleanExtra("open_settings", false)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = if (openSettings) {
                R.id.nav_settings
            } else {
                R.id.nav_home
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
