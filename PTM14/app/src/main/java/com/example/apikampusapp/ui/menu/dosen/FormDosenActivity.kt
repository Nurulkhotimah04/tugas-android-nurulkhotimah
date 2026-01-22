package com.example.apikampusapp.ui.menu.dosen

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apikampusapp.Network.ApiClient
import com.example.apikampusapp.R
import com.example.apikampusapp.model.DosenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormDosenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_dosen)

        val etNidn = findViewById<EditText>(R.id.etNidn)
        val etNama = findViewById<EditText>(R.id.etNama)
        val spJabatan = findViewById<Spinner>(R.id.spJabatan)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val jabatan = arrayOf("Lektor", "Asisten Ahli", "Profesor")
        spJabatan.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jabatan)

        btnSimpan.setOnClickListener {

            val nidn = etNidn.text.toString()
            val nama = etNama.text.toString()
            val jab = spJabatan.selectedItem.toString()

            // ✅ Validasi (Nilai Plus)
            if (nidn.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ApiClient.instance.insertDosen(nidn, nama, jab)
                .enqueue(object : Callback<DosenResponse> {

                    override fun onResponse(
                        call: Call<DosenResponse>,
                        response: Response<DosenResponse>
                    ) {
                        if (response.body()?.kode == 1) {
                            Toast.makeText(
                                this@FormDosenActivity,
                                "Berhasil",
                                Toast.LENGTH_SHORT
                            ).show()

                            // ✅ Logcat (Nilai Plus)
                            Log.d("DOSEN", "Data: $nidn - $nama - $jab")
                        } else {
                            Toast.makeText(
                                this@FormDosenActivity,
                                "Gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<DosenResponse>, t: Throwable) {
                        Toast.makeText(
                            this@FormDosenActivity,
                            "Error Jaringan: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}