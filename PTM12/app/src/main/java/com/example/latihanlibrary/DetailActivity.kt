package com.example.latihanlibrary

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

/**
 * Activity untuk menampilkan detail user
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Inisialisasi komponen UI
        val imgAvatar = findViewById<ImageView>(R.id.img_avatar_detail)
        val tvName = findViewById<TextView>(R.id.tv_name_detail)
        val tvEmail = findViewById<TextView>(R.id.tv_email_detail)

        // Ambil data dari Intent
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val email = intent.getStringExtra("email")
        val avatar = intent.getStringExtra("avatar")

        // Tampilkan data ke UI
        tvName.text = "$firstName $lastName"
        tvEmail.text = email

        // Load avatar menggunakan Glide
        Glide.with(this)
            .load(avatar)
            .into(imgAvatar)
    }
}
