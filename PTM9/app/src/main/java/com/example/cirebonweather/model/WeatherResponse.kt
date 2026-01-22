package com.example.cirebonweather.model
import com.google.gson.annotations.SerializedName

// 1. Wadah Utama (Root)
data class WeatherResponse(
    @SerializedName("hourly") val hourly: HourlyData
)
// 2. Wadah Data Per Jam (Sesuai struktur JSON)
data class HourlyData(
    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m") val temperature: List<Double>
)
// 3. Model untuk ditampilkan di List (Hasil gabungan Time & Temp)
data class WeatherItem(
    val time: String,
    val temperature: String
)