package com.example.krut_bar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

data class Menu_pos(val name:  String, val url:  String)

class MenuViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val contactName: TextView = root.name
    val contactPhoneNumber: ImageView = root.image
}

class MenuAdapter(
    private val users: List<Menu_pos>,
    val onClick: (Menu_pos) -> Unit
) : RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        val holder = MenuViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
        holder.root.setOnClickListener {
            onClick(users[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.contactName.text =
            users[position].name
        Picasso.get()
            .load(users[position].url)
            .into(holder.contactPhoneNumber)
    }

}

