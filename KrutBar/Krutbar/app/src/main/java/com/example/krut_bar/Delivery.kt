package com.example.krut_bar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.menu.*

class Delivery : Fragment() {

    private val root = FirebaseDatabase.getInstance().reference.root
    private var set = mutableSetOf<String>()
    private var ans: MutableList<Del_pos> = mutableListOf()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.delivery, null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        read()
        return v
    }

    private fun read() {
        root.child("delivery").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>
                set.addAll(map.keys as Set<String>)
                for (name in set) {
                    read(name)
                }
                my_recycler_view.adapter = DeliveryAdapter(ans) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    startActivity(browserIntent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun read(name: String) {
        root.child("delivery").child(name).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>
                ans.add(Del_pos(name, map["link"].toString(),map["url"].toString()))
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
