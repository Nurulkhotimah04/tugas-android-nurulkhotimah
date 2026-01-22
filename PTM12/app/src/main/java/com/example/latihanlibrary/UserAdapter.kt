package com.example.latihanlibrary

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.latihanlibrary.model.User

/**
 * Adapter RecyclerView
 * Menghubungkan data user dengan tampilan item_user.xml
 */
class UserAdapter(
    private val listUsers: List<User>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    /**
     * ViewHolder
     * Menyimpan referensi ke komponen UI di item_user.xml
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
    }

    // Membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    // Jumlah item yang ditampilkan
    override fun getItemCount(): Int = listUsers.size

    // Mengisi data ke dalam ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUsers[position]

        // Set data ke TextView
        holder.tvName.text = "${user.firstName} ${user.lastName}"
        holder.tvEmail.text = user.email

        // Load gambar avatar menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.imgAvatar)

        // Event klik item RecyclerView
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("firstName", user.firstName)
            intent.putExtra("lastName", user.lastName)
            intent.putExtra("email", user.email)
            intent.putExtra("avatar", user.avatar)
            holder.itemView.context.startActivity(intent)
        }
    }
}
