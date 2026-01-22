package com.example.apikampusapp.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    // GANTI DENGAN IP LAPTOP ANDA (Jangan localhost)
    private const val BASE_URL = "http://10.0.2.2/api_kampus/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}