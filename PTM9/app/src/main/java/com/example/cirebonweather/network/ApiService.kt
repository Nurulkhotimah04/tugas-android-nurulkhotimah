package com.example.cirebonweather.network
import com.example.cirebonweather.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
interface ApiService {
    @GET("v1/forecast?latitude=-6.7&longitude=108.5&hourly=temperature_2m")
//    fun getCuacaCirebon(): Call<WeatherResponse>
}