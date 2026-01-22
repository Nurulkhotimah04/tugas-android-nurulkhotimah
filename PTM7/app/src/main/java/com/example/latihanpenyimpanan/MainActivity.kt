package com.example.latihanpenyimpanan

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    // ===================== KOMPONEN DARK MODE =====================
    // Switch untuk mengaktifkan / menonaktifkan mode gelap
    private lateinit var switchModeGelap: Switch

    // ===================== KOMPONEN SHARED PREFERENCES =====================
    // Input nama pengguna
    private lateinit var editTextNamaPengguna: EditText
    // Tombol simpan nama
    private lateinit var buttonSimpanNama: Button
    // Tombol hapus nama
    private lateinit var buttonHapusNama: Button
    // TextView untuk menampilkan hasil nama
    private lateinit var textViewHasilNama: TextView

    // ===================== KOMPONEN INTERNAL STORAGE =====================
    // Input catatan
    private lateinit var editTextCatatan: EditText
    // Tombol simpan catatan ke file
    private lateinit var buttonSimpanCatatan: Button
    // Tombol baca catatan dari file
    private lateinit var buttonBacaCatatan: Button

    // ===================== KONSTANTA =====================
    // Nama file SharedPreferences
    private val PREF_NAME = "data_latihan_pref"
    // Key untuk menyimpan nama user
    private val KEY_NAMA_USER = "key_nama_user"
    // Key untuk status dark mode
    private val KEY_DARK_MODE = "is_dark_mode"
    // Nama file untuk internal storage
    private val FILE_NAME = "catatan_harian.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi semua komponen UI
        inisialisasiKomponen()

        // Menjalankan fitur SharedPreferences
        setupSharedPreferences()

        // Menjalankan fitur Internal Storage
        setupInternalStorage()
    }

    // ===================== INISIALISASI KOMPONEN =====================
    private fun inisialisasiKomponen() {
        switchModeGelap = findViewById(R.id.switch_mode_gelap)
        editTextNamaPengguna = findViewById(R.id.edit_text_nama_pengguna)
        buttonSimpanNama = findViewById(R.id.button_simpan_nama)
        buttonHapusNama = findViewById(R.id.button_hapus_nama)
        textViewHasilNama = findViewById(R.id.text_view_hasil_nama)
        editTextCatatan = findViewById(R.id.edit_text_catatan)
        buttonSimpanCatatan = findViewById(R.id.button_simpan_catatan)
        buttonBacaCatatan = findViewById(R.id.button_baca_catatan)
    }

    // ===================== SHARED PREFERENCES =====================
    private fun setupSharedPreferences() {

        // Mengambil SharedPreferences dengan mode PRIVATE
        val sharedPreferences =
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Ambil status dark mode yang tersimpan
        val isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        switchModeGelap.isChecked = isDarkMode
        terapkanDarkMode(isDarkMode)

        // Listener untuk switch dark mode
        switchModeGelap.setOnCheckedChangeListener { _, isChecked ->
            // Simpan status dark mode
            sharedPreferences.edit()
                .putBoolean(KEY_DARK_MODE, isChecked)
                .apply()

            // Terapkan tampilan sesuai mode
            terapkanDarkMode(isChecked)
        }

        // Mengecek apakah nama user sudah tersimpan
        val namaTersimpan = sharedPreferences.getString(KEY_NAMA_USER, null)
        if (namaTersimpan != null) {
            textViewHasilNama.text =
                "Halo, $namaTersimpan (Data dari Storage)"
        }

        // ================= SIMPAN NAMA =================
        buttonSimpanNama.setOnClickListener {
            val namaInput = editTextNamaPengguna.text.toString()

            // Menyimpan nama ke SharedPreferences
            sharedPreferences.edit()
                .putString(KEY_NAMA_USER, namaInput)
                .apply()

            textViewHasilNama.text =
                "Halo, $namaInput (Baru disimpan)"

            Toast.makeText(
                this,
                "Nama berhasil disimpan!",
                Toast.LENGTH_SHORT
            ).show()
        }

        // ================= HAPUS NAMA =================
        buttonHapusNama.setOnClickListener {
            // Menghapus data nama
            sharedPreferences.edit()
                .remove(KEY_NAMA_USER)
                .apply()

            textViewHasilNama.text = "Status: Data dihapus"
            editTextNamaPengguna.text.clear()

            Toast.makeText(
                this,
                "Data berhasil dihapus!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // ===================== INTERNAL STORAGE =====================
    private fun setupInternalStorage() {

        // ================= SIMPAN CATATAN =================
        buttonSimpanCatatan.setOnClickListener {
            val isiCatatan = editTextCatatan.text.toString()

            // Menyimpan teks ke file internal storage
            openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(isiCatatan.toByteArray())
            }

            Toast.makeText(
                this,
                "Catatan disimpan ke file!",
                Toast.LENGTH_SHORT
            ).show()

            editTextCatatan.text.clear()
        }

        // ================= BACA CATATAN =================
        buttonBacaCatatan.setOnClickListener {
            val file = File(filesDir, FILE_NAME)

            // Cek apakah file ada
            if (file.exists()) {
                val isiFile = openFileInput(FILE_NAME)
                    .bufferedReader()
                    .use { it.readText() }

                editTextCatatan.setText(isiFile)

                Toast.makeText(
                    this,
                    "File berhasil dibaca!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "File tidak ditemukan!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // ===================== DARK MODE =====================
    private fun terapkanDarkMode(isDarkMode: Boolean) {

        // Mengambil root layout
        val rootLayout = findViewById<ScrollView>(R.id.root_layout)

        if (isDarkMode) {
            // Mode gelap
            rootLayout.setBackgroundColor(android.graphics.Color.DKGRAY)

            textViewHasilNama.setTextColor(android.graphics.Color.WHITE)
            editTextNamaPengguna.setTextColor(android.graphics.Color.WHITE)
            editTextCatatan.setTextColor(android.graphics.Color.WHITE)
            switchModeGelap.setTextColor(android.graphics.Color.WHITE)
        } else {
            // Mode terang
            rootLayout.setBackgroundColor(android.graphics.Color.WHITE)

            textViewHasilNama.setTextColor(android.graphics.Color.BLACK)
            editTextNamaPengguna.setTextColor(android.graphics.Color.BLACK)
            editTextCatatan.setTextColor(android.graphics.Color.BLACK)
            switchModeGelap.setTextColor(android.graphics.Color.BLACK)
        }
    }
}
