package com.example.praktikumnavigasi

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class DetailActivity : AppCompatActivity() {

    private lateinit var btnHome: Button
    private lateinit var btnProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btnHome = findViewById(R.id.btnFragment1)
        btnProfile = findViewById(R.id.btnFragment2)

        // Default: Home Fragment → tombol tetap tampil
        replaceFragment(HomeFragmentActivity(), showNav = true)

        btnHome.setOnClickListener {
            replaceFragment(HomeFragmentActivity(), showNav = true)
        }

        btnProfile.setOnClickListener {
            replaceFragment(ProfileFragmentActifivity(), showNav = false)
        }
    }

    private fun replaceFragment(fragment: Fragment, showNav: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        // Kontrol visibilitas tombol Activity
        if (showNav) {
            btnHome.visibility = Button.VISIBLE
            btnProfile.visibility = Button.VISIBLE
        } else {
            btnHome.visibility = Button.GONE
            btnProfile.visibility = Button.GONE
        }
    }
}
