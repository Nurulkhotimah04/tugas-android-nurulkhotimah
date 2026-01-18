package com.example.layoutdasar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var button_login: Button
    private lateinit var button_register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button_login = findViewById(R.id.buttonLogin)
        button_register = findViewById(R.id.buttonRegister)

        button_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        button_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
