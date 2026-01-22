package com.example.luaspersegipanjang

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

        // Deklarasi variabel
        val editTextPanjangPersegi =
            findViewById<EditText>(R.id.edit_text_panjang_persegi)
        val editTextLebarPersegi =
            findViewById<EditText>(R.id.edit_text_lebar_persegi)
        val buttonHitungLuas =
            findViewById<Button>(R.id.button_hitung_luas)
        val textViewHasilLuas =
            findViewById<TextView>(R.id.text_view_hasil_luas)

        buttonHitungLuas.setOnClickListener {

            val panjang = editTextPanjangPersegi.text.toString()
            val lebar = editTextLebarPersegi.text.toString()

            // Validasi agar tidak crash
            if (panjang.isEmpty() || lebar.isEmpty()) {
                Toast.makeText(
                    this,
                    "Panjang dan Lebar harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val panjangValue = panjang.toDouble()
                val lebarValue = lebar.toDouble()

                // Rumus Luas
                val luas = panjangValue * lebarValue

                // Tampilkan hasil
                textViewHasilLuas.text = "Hasil Luas: $luas"
            }
        }
    }
}
