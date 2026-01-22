package com.example.latihansqlite
    import android.content.ContentValues
    import android.content.Context
    import android.database.Cursor
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {
    // Companion Object untuk konstanta agar mudah diakses dan minim typo
    companion object {
        private const val DATABASE_NAME = "kampus.db"
        private const val DATABASE_VERSION = 1
        // Nama Tabel dan Kolom
        const val TABLE_MAHASISWA = "mahasiswa"
        const val COLUMN_NIM = "nim"
        const val COLUMN_NAMA = "nama"
    }
    // 1. Membuat Tabel saat database pertama kali dibuat
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_MAHASISWA (" +
                "$COLUMN_NIM TEXT PRIMARY KEY," +
                "$COLUMN_NAMA TEXT)")
        db?.execSQL(createTableQuery)
    }
    // 2. Menangani perubahan versi database
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int,
                           newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        onCreate(db)
    }
    // --- OPERASI CRUD ---
// CREATE: Tambah Data
    fun tambahData(mahasiswa: Mahasiswa): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NIM, mahasiswa.nim)
        values.put(COLUMN_NAMA, mahasiswa.nama)
// Mengembalikan -1 jika gagal, atau ID baris jika sukses
        val result = db.insert(TABLE_MAHASISWA, null, values)
        db.close()
        return result
    }
    // READ: Ambil Semua Data
    fun tampilkanSemuaData(): ArrayList<String> {
        val listData = ArrayList<String>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_MAHASISWA"
        val cursor: Cursor? = db.rawQuery(query, null)
// Looping cursor untuk mengambil data
        if (cursor!!.moveToFirst()) {
            do {
                val nim =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
                val nama =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
                listData.add("$nim - $nama")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listData
    }
    // DELETE: Hapus Data Berdasarkan NIM
    fun hapusData(nim: String): Int {
        val db = this.writableDatabase
// Menghapus baris di mana clause "nim = ?" sesuai argumen
        val result = db.delete(TABLE_MAHASISWA, "$COLUMN_NIM = ?",
            arrayOf(nim))
        db.close()
        return result
    }

    // UPDATE: Update Nama Mahasiswa berdasarkan NIM
    fun updateData(mahasiswa: Mahasiswa): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAMA, mahasiswa.nama)

        val result = db.update(
            TABLE_MAHASISWA,
            values,
            "$COLUMN_NIM = ?",
            arrayOf(mahasiswa.nim)
        )

        db.close()
        return result
    }

    //Cek NIM
    fun cekNimAda(nim: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_NIM FROM $TABLE_MAHASISWA WHERE $COLUMN_NIM = ?",
            arrayOf(nim)
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    //Edit Nama dan NIM
    fun updateNimDanNama(
        nimLama: String,
        nimBaru: String,
        namaBaru: String
    ): Boolean {

        val db = this.writableDatabase

        try {
            // 🔍 Cek NIM baru (PAKAI DB YANG SAMA)
            if (nimLama != nimBaru) {
                val cursor = db.rawQuery(
                    "SELECT $COLUMN_NIM FROM $TABLE_MAHASISWA WHERE $COLUMN_NIM = ?",
                    arrayOf(nimBaru)
                )
                val exists = cursor.count > 0
                cursor.close()

                if (exists) return false
            }

            val values = ContentValues()
            values.put(COLUMN_NIM, nimBaru)
            values.put(COLUMN_NAMA, namaBaru)

            val rows = db.update(
                TABLE_MAHASISWA,
                values,
                "$COLUMN_NIM = ?",
                arrayOf(nimLama)
            )

            return rows > 0

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            db.close()
        }
    }

}