package com.example.aritmatika

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtAngka1 = findViewById<EditText>(R.id.edtAngka1)
        val edtAngka2 = findViewById<EditText>(R.id.edtAngka2)
        val txtHasil = findViewById<TextView>(R.id.txtHasil)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnKurang = findViewById<Button>(R.id.btnKurang)
        val btnKali = findViewById<Button>(R.id.btnKali)
        val btnBagi = findViewById<Button>(R.id.btnBagi)

        fun getInput(): Pair<Double, Double>? {
            val a = edtAngka1.text.toString()
            val b = edtAngka2.text.toString()

            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Masukkan kedua angka", Toast.LENGTH_SHORT).show()
                return null
            }
            return Pair(a.toDouble(), b.toDouble())
        }

        btnKurang.setOnClickListener {
            val data = getInput() ?: return@setOnClickListener
            txtHasil.text = "Hasil: ${data.first - data.second}"
        }

        btnTambah.setOnClickListener {
            val data = getInput() ?: return@setOnClickListener
            txtHasil.text = "Hasil: ${data.first + data.second}"
        }

        btnKali.setOnClickListener {
            val data = getInput() ?: return@setOnClickListener
            txtHasil.text = "Hasil: ${data.first * data.second}"
        }

        btnBagi.setOnClickListener {
            val data = getInput() ?: return@setOnClickListener
            if (data.second == 0.0) {
                Toast.makeText(this, "Tidak bisa dibagi 0", Toast.LENGTH_SHORT).show()
            } else {
                txtHasil.text = "Hasil: ${data.first / data.second}"
            }
        }
    }
}
