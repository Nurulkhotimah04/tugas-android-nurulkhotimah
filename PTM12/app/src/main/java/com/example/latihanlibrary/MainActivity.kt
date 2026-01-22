package com.example.latihanlibrary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanlibrary.model.UserResponse
import com.example.latihanlibrary.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // RecyclerView untuk menampilkan list user
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.rv_users)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Ambil data dari API
        getDataFromApi()
    }

    /**
     * Fungsi untuk mengambil data user dari API menggunakan Retrofit
     */
    private fun getDataFromApi() {

        // Panggil endpoint API
        ApiClient.instance.getUsers(1).enqueue(object : Callback<UserResponse> {

            // Dipanggil ketika response berhasil diterima
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {

                    // Ambil list user dari response body
                    val listUsers = response.body()?.data

                    if (listUsers != null) {
                        // Set adapter ke RecyclerView
                        val adapter = UserAdapter(listUsers)
                        recyclerView.adapter = adapter
                    }

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal memuat data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Dipanggil jika terjadi error (tidak ada internet, timeout, dll)
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
