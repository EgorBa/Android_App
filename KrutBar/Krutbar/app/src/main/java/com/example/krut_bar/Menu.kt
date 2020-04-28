package com.example.krut_bar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.menu.*

class Menu : Fragment() {

    private val root = FirebaseDatabase.getInstance().reference.root
    private var set = mutableSetOf<String>()
    private var ans: MutableList<Menu_pos> = mutableListOf()
    private var progress: ImageView? = null
    private var background: FrameLayout? = null
    private var recyclerView1: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.menu, null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView1 = v.findViewById(R.id.my_recycler_view)
        progress = v.findViewById(R.id.progress)
        background = v.findViewById(R.id.background)
        recyclerView.layoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)
        read()
        return v
    }

    private fun read() {
        val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
        progress!!.visibility = View.VISIBLE
        background!!.visibility = View.VISIBLE
        recyclerView1!!.visibility = View.INVISIBLE
        progress!!.animation = rotate
        rotate.repeatCount = 100
        root.child("menu").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>
                set.addAll(map.keys as Set<String>)
                for (name in set) {
                    read(name)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun read(name: String) {
        val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
        root.child("menu").child(name).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<String, *>
                ans.add(Menu_pos(name, map["link"].toString()))
                my_recycler_view.adapter = MenuAdapter(ans) {
                    val intent = Intent(context,AddMenu::class.java)
                    intent.putExtra(AddMenu.ID,it.name)
                    startActivity(intent)
                }
                rotate.repeatCount = 0
                progress!!.visibility = View.INVISIBLE
                background!!.visibility = View.INVISIBLE
                my_recycler_view.visibility = View.VISIBLE
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
