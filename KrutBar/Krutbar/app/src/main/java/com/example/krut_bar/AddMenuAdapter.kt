package com.example.krut_bar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_addmenu.view.*

data class Add_menu_pos(val name: String, val url: String, val price: String, val weight: String)

class AddMenuViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val contactName: TextView = root.name
    val contactPrice: TextView = root.price
    val contactWeight: TextView = root.weight
    val contactPhoneNumber: ImageView = root.image
}

class AddMenuAdapter(
    private val users: List<Add_menu_pos>,
    val onClick: (Add_menu_pos) -> Unit
) : RecyclerView.Adapter<AddMenuViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddMenuViewHolder {
        val holder = AddMenuViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_addmenu,
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

    override fun onBindViewHolder(holder: AddMenuViewHolder, position: Int) {
        holder.contactName.text =
            users[position].name
        holder.contactPrice.text =
            users[position].price
        holder.contactWeight.text =
            users[position].weight
        if (users[position].url != " ") {
            Picasso.get()
                .load(users[position].url)
                .error(R.drawable.ic_close_black_24dp)
                .into(holder.contactPhoneNumber)
        }
    }

}

