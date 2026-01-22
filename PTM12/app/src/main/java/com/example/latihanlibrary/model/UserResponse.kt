package com.example.latihanlibrary.model

import com.google.gson.annotations.SerializedName

/**
 * Data class utama (wrapper)
 * Digunakan karena struktur JSON dari API berbentuk:
 * {
 *   "page": 1,
 *   "per_page": 6,
 *   "total": 12,
 *   "total_pages": 2,
 *   "data": [ ... ]
 * }
 */
data class UserResponse(

    // Mengambil array "data" dari JSON
    @SerializedName("data")
    val data: List<User>
)

/**
 * Data class User
 * Mewakili satu objek user pada array "data"
 */
data class User(

    // ID user
    @SerializedName("id")
    val id: Int,

    // Email user
    @SerializedName("email")
    val email: String,

    // Nama depan user
    @SerializedName("first_name")
    val firstName: String,

    // Nama belakang user
    @SerializedName("last_name")
    val lastName: String,

    // URL avatar user
    @SerializedName("avatar")
    val avatar: String
)
