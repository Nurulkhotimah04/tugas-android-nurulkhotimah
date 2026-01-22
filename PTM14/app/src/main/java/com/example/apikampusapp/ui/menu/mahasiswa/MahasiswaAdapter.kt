package com.example.apikampusapp.ui.menu.mahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apikampusapp.R
import com.example.apikampusapp.model.DataMahasiswa

// Adapter RecyclerView untuk menampilkan data mahasiswa
class MahasiswaAdapter(private val list: List<DataMahasiswa>) :
    RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNim: TextView = v.findViewById(R.id.tvNim)
        val tvNama: TextView = v.findViewById(R.id.tvNama)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mahasiswa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNim.text = list[position].nim
        holder.tvNama.text = list[position].nama
    }

    override fun getItemCount(): Int = list.size
}
