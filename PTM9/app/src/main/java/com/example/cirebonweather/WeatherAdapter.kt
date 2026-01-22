package com.example.cirebonweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cirebonweather.model.WeatherItem

class WeatherAdapter(private val weatherList: List<WeatherItem>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewJam: TextView = itemView.findViewById(R.id.text_view_jam)
        val textViewSuhu: TextView = itemView.findViewById(R.id.text_view_suhu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = weatherList[position]

        // Memformat tampilan waktu agar lebih rapi (menghapus huruf T pada format ISO)
        val jamBersih = item.time.replace("T", " Pukul ")

        holder.textViewJam.text = jamBersih

        // Pastikan suhu diubah ke string jika perlu
        holder.textViewSuhu.text = item.temperature.toString()
    }

    override fun getItemCount(): Int = weatherList.size
}
