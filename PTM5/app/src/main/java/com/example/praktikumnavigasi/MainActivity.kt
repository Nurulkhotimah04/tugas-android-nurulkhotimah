package com.example.praktikumnavigasi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val btnMove = findViewById<Button>(R.id.btnMove)

        btnMove.setOnClickListener {
            val nameInput = etName.text.toString().trim()

            if (nameInput.isEmpty()) {
                etName.error = "Nama tidak boleh kosong"
                etName.requestFocus()
                return@setOnClickListener
            }

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("EXTRA_NAME", nameInput)
            startActivity(intent)
        }
    }
}