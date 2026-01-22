package com.example.apikampusapp.Network

import com.example.apikampusapp.model.DosenResponse
import com.example.apikampusapp.model.ResponseMahasiswa
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // 1. Fungsi READ (Mengakses read_mahasiswa.php)
    @GET("read_mahasiswa.php")
    fun getMahasiswa(): Call<ResponseMahasiswa>
    // 2. Fungsi CREATE (Mengakses create_mahasiswa.php)
// Gunakan @FormUrlEncoded untuk POST request ke PHP Native
    @FormUrlEncoded
    @POST("create_mahasiswa.php")
    fun insertMahasiswa(
        @Field("nim") nim: String,
        @Field("nama_lengkap") nama: String,
        @Field("jenis_kelamin") jk: String,
        @Field("id_prodi") idProdi: Int
    ): Call<ResponseMahasiswa>

    @GET("read_dosen.php")
    fun getDosen(): Call<DosenResponse>

    @FormUrlEncoded
    @POST("create_dosen.php")
    fun insertDosen(
        @Field("nidn") nidn: String,
        @Field("nama_dosen") nama: String,
        @Field("jabatan") jabatan: String
    ): Call<DosenResponse>

}

