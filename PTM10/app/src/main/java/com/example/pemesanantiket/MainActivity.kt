package com.example.pemesanantiket

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "tiket_channel"
    private val NOTIF_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buatNotificationChannel()
        mintaIzinNotifikasi()

        val etNama = findViewById<EditText>(R.id.etNama)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)
        val btnPesan = findViewById<Button>(R.id.btnPesan)

        btnPesan.setOnClickListener {
            val nama = etNama.text.toString()
            val jumlah = etJumlah.text.toString()

            if (nama.isBlank() || jumlah.isBlank()) {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                prosesPesanan(nama, jumlah)
            }
        }

        // JIKA APP DIBUKA DARI NOTIFIKASI
        tampilkanDialogDariNotifikasi()
    }

    // ==========================
    // LOADING 2 DETIK
    // ==========================
    private fun prosesPesanan(nama: String, jumlah: String) {
        val loading = ProgressDialog(this)
        loading.setMessage("Memproses pesanan...")
        loading.setCancelable(false)
        loading.show()

        Handler(mainLooper).postDelayed({
            loading.dismiss()
            tampilkanNotifikasi(nama, jumlah)
        }, 2000)
    }

    // ==========================
    // NOTIFICATION CHANNEL
    // ==========================
    private fun buatNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pemesanan Tiket",
                NotificationManager.IMPORTANCE_HIGH
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    // ==========================
    // IZIN ANDROID 13+
    // ==========================
    private fun mintaIzinNotifikasi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

    // ==========================
    // TAMPILKAN NOTIFIKASI
    // ==========================
    private fun tampilkanNotifikasi(nama: String, jumlah: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("nama", nama)
            putExtra("jumlah", jumlah)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notif = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Pemesanan Tiket")
            .setContentText("Tiket atas nama $nama berhasil dipesan")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(this).notify(NOTIF_ID, notif)
    }

    // ==========================
    // ALERT DIALOG SAAT NOTIF DIKLIK
    // ==========================
    private fun tampilkanDialogDariNotifikasi() {
        val nama = intent.getStringExtra("nama")
        val jumlah = intent.getStringExtra("jumlah")

        if (nama != null && jumlah != null) {
            AlertDialog.Builder(this)
                .setTitle("Detail Pesanan")
                .setMessage("Nama: $nama\nJumlah Tiket: $jumlah")
                .setPositiveButton("OK", null)
                .show()

            intent.removeExtra("nama")
            intent.removeExtra("jumlah")
        }
    }
}
