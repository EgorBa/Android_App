package com.example.krut_bar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

data class Del_pos(val name:  String, val link:  String, val url: String)

class DeliveryViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val contactName: TextView = root.name
    val contactPhoneNumber: ImageView = root.image
}

class DeliveryAdapter (
    private val users: List<Del_pos>,
    val onClick: (Del_pos) -> Unit
) : RecyclerView.Adapter<DeliveryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryViewHolder {
        val holder = DeliveryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_delivery,
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

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.contactName.text =
            users[position].name
        Picasso.get()
            .load(users[position].link)
            .into(holder.contactPhoneNumber)
    }

}

