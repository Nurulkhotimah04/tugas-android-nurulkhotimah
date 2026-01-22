package com.example.apikampusapp.ui.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apikampusapp.R
import com.example.apikampusapp.ui.menu.dosen.FormDosenActivity
import com.example.apikampusapp.ui.menu.mahasiswa.MahasiswaActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnMahasiswa).setOnClickListener {
            startActivity(Intent(this, MahasiswaActivity::class.java))
        }

        findViewById<Button>(R.id.btnDosen).setOnClickListener {
            startActivity(Intent(this, FormDosenActivity::class.java))
        }
    }
}
