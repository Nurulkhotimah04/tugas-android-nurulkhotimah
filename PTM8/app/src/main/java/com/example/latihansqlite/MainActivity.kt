package com.example.latihansqlite

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNim: EditText
    private lateinit var editTextNama: EditText
    private lateinit var buttonSimpan: Button
    private lateinit var buttonUpdate: Button
    private lateinit var buttonHapus: Button
    private lateinit var listViewData: ListView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: ArrayAdapter<String>

    private var nimLama: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inisialisasiKomponen()
        databaseHelper = DatabaseHelper(this)
        muatData()

        // SIMPAN
        buttonSimpan.setOnClickListener {
            val nim = editTextNim.text.toString().trim()
            val nama = editTextNama.text.toString().trim()

            if (nim.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = databaseHelper.tambahData(Mahasiswa(nim, nama))
            if (result > -1) {
                Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
                bersihkanInput()
                muatData()
            } else {
                Toast.makeText(this, "NIM sudah ada", Toast.LENGTH_SHORT).show()
            }
        }

        // UPDATE
        buttonUpdate.setOnClickListener {
            val nimBaru = editTextNim.text.toString().trim()
            val namaBaru = editTextNama.text.toString().trim()

            if (nimLama.isEmpty()) {
                Toast.makeText(this, "Pilih data dari list", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sukses = databaseHelper.updateNimDanNama(
                nimLama,
                nimBaru,
                namaBaru
            )

            if (sukses) {
                Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                nimLama = ""
                bersihkanInput()
                muatData()
            } else {
                Toast.makeText(this, "NIM sudah digunakan", Toast.LENGTH_SHORT).show()
            }
        }

        // HAPUS
        buttonHapus.setOnClickListener {
            val nim = editTextNim.text.toString().trim()

            if (nim.isEmpty()) {
                Toast.makeText(this, "Masukkan NIM", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = databaseHelper.hapusData(nim)
            if (result > 0) {
                Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show()
                bersihkanInput()
                muatData()
            } else {
                Toast.makeText(this, "NIM tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        // KLIK LIST → ISI FORM
        listViewData.setOnItemClickListener { _, _, position, _ ->
            val data = adapter.getItem(position)!!
            val split = data.split(" - ")

            nimLama = split[0]
            editTextNim.setText(split[0])
            editTextNama.setText(split[1])
        }
    }

    // ================== FUNGSI ==================

    private fun inisialisasiKomponen() {
        editTextNim = findViewById(R.id.edit_text_nim_mahasiswa)
        editTextNama = findViewById(R.id.edit_text_nama_mahasiswa)
        buttonSimpan = findViewById(R.id.button_simpan_data)
        buttonUpdate = findViewById(R.id.button_update_data)
        buttonHapus = findViewById(R.id.button_hapus_data)
        listViewData = findViewById(R.id.list_view_data_mahasiswa)
    }

    private fun muatData() {
        val data = databaseHelper.tampilkanSemuaData()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listViewData.adapter = adapter
    }

    private fun bersihkanInput() {
        editTextNim.text.clear()
        editTextNama.text.clear()
        nimLama = ""
    }
}
