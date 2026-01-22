package com.example.latihanlibrary.network

import com.example.latihanlibrary.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ApiService
 * Berisi definisi endpoint API
 */
interface ApiService {

    /**
     * GET https://reqres.in/api/users?page=1
     *
     * @param page nomor halaman data
     * @return Call<UserResponse>
     */
    @GET("users")
    fun getUsers(
        @Query("page") page: Int
    ): Call<UserResponse>
}
