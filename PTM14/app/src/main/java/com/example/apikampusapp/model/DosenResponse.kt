package com.example.apikampusapp.model

import com.google.gson.annotations.SerializedName

data class DosenResponse(
    @SerializedName("kode") val kode: Int,
    @SerializedName("pesan") val pesan: String,
    @SerializedName("data") val data: List<Dosen>? = null
)

data class Dosen(
    @SerializedName("nidn") val nidn: String,
    @SerializedName("nama_dosen") val nama: String,
    @SerializedName("jabatan") val jabatan: String
)
