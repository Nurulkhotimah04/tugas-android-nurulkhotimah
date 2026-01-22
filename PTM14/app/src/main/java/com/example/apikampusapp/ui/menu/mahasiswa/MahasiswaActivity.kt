package com.example.apikampusapp.ui.menu.mahasiswa

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apikampusapp.Network.ApiClient
import com.example.apikampusapp.R
import com.example.apikampusapp.model.DataMahasiswa
import com.example.apikampusapp.model.ResponseMahasiswa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MahasiswaActivity : AppCompatActivity() {

    private lateinit var adapter: MahasiswaAdapter
    private val listMahasiswa = mutableListOf<DataMahasiswa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mahasiswa)

        // =========================
        // Inisialisasi View
        // =========================
        val etNim = findViewById<EditText>(R.id.etNim)
        val etNama = findViewById<EditText>(R.id.etNama)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val rvMahasiswa = findViewById<RecyclerView>(R.id.rvMahasiswa)

        // =========================
        // Setup RecyclerView
        // =========================
        adapter = MahasiswaAdapter(listMahasiswa)
        rvMahasiswa.layoutManager = LinearLayoutManager(this)
        rvMahasiswa.adapter = adapter

        // Load data awal
        loadMahasiswa()

        // =========================
        // Tombol Simpan
        // =========================
        btnSimpan.setOnClickListener {

            val nim = etNim.text.toString()
            val nama = etNama.text.toString()

            // Validasi input
            if (nim.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "NIM dan Nama wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil API Insert
            ApiClient.instance.insertMahasiswa(
                nim,
                nama,
                "-",   // jenis_kelamin dummy (karena tidak ada di UI)
                1      // id_prodi (sementara)
            ).enqueue(object : Callback<ResponseMahasiswa> {

                override fun onResponse(
                    call: Call<ResponseMahasiswa>,
                    response: Response<ResponseMahasiswa>
                ) {
                    Log.d("INSERT", response.body().toString())

                    if (response.isSuccessful && response.body()?.kode == 1) {
                        Toast.makeText(
                            this@MahasiswaActivity,
                            "Berhasil simpan mahasiswa",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Reset input
                        etNim.text.clear()
                        etNama.text.clear()

                        // Reload data
                        loadMahasiswa()
                    } else {
                        Toast.makeText(
                            this@MahasiswaActivity,
                            "Gagal simpan data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseMahasiswa>, t: Throwable) {
                    Log.e("ERROR", t.message.toString())
                    Toast.makeText(
                        this@MahasiswaActivity,
                        "Error jaringan: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    // =========================
    // Load Mahasiswa
    // =========================
    private fun loadMahasiswa() {
        ApiClient.instance.getMahasiswa()
            .enqueue(object : Callback<ResponseMahasiswa> {
                override fun onResponse(
                    call: Call<ResponseMahasiswa>,
                    response: Response<ResponseMahasiswa>
                ) {
                    listMahasiswa.clear()
                    response.body()?.data?.let {
                        listMahasiswa.addAll(it)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ResponseMahasiswa>, t: Throwable) {
                    Log.e("LOAD", t.message.toString())
                }
            })
    }
}
