package com.example.apikampusapp.model

import com.google.gson.annotations.SerializedName
// 1. Wadah Utama Response
data class ResponseMahasiswa(
    @SerializedName("kode") val kode: Int,
    @SerializedName("pesan") val pesan: String,
    @SerializedName("data") val data: List<DataMahasiswa>? = null
)
// 2. Model Data Mahasiswa (Sesuai field JSON)
data class DataMahasiswa(
    @SerializedName("nim") val nim: String,
    @SerializedName("nama_lengkap") val nama: String,
    @SerializedName("jenis_kelamin") val jk: String,
    @SerializedName("nama_prodi") val prodi: String
)