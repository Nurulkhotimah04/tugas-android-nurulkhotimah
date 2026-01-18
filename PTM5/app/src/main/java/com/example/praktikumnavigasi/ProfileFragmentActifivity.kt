package com.example.praktikumnavigasi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ProfileFragmentActifivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_profile_fragment_actifivity, container, false)

        // Inisialisasi tombol
        val btnToAbout = view.findViewById<Button>(R.id.btnToAbout)

        // Tambahkan onClickListener
        btnToAbout.setOnClickListener {
            // Gunakan requireActivity() untuk mendapatkan context dari Fragment
            val intent = Intent(requireActivity(), AboutActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
