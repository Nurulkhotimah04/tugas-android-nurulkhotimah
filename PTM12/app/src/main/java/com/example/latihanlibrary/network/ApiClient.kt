package com.example.latihanlibrary.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiClient
 * Digunakan untuk membuat instance Retrofit
 */
object ApiClient {

    // Base URL dari API (harus diakhiri dengan "/")
    private const val BASE_URL = "https://reqres.in/api/"

    /**
     * Instance ApiService
     * lazy → object dibuat hanya saat pertama kali dipanggil
     */
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL dasar API
            .addConverterFactory(GsonConverterFactory.create()) // Konversi JSON ke Object
            .build()
            .create(ApiService::class.java)
    }
}
