package com.example.krut_bar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_menu1.*
import java.util.*

class AddMenu : AppCompatActivity() {

    private val root = FirebaseDatabase.getInstance().reference.root
    //private var list = LinkedList<String>()
    private var set = mutableSetOf<String>()
    private var ans: MutableList<Add_menu_pos> = mutableListOf()

    companion object{
        const val ID = "ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu1)
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        read()
    }

    private fun read() {
        val category = intent.getStringExtra(ID)
        root.child("menu").child(category)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map = dataSnapshot.value as Map<*, *>
                    //map.putAll(dataSnapshot.value as Map<String, Object>)
                    var list: LinkedList<String> = LinkedList()
                    set.addAll(map.keys as Set<String>)
                    for (name in set) {
                        if (name != "link") {
                            list.add(name)
                        }
                    }
                    read(list)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    private fun read(list: LinkedList<String>) {
        val category = intent.getStringExtra(ID)
        if (list.isEmpty()) {
            my_recycler_view.adapter = AddMenuAdapter(ans) {
                val intent = Intent(this, PositionActivity::class.java)
                intent.putExtra(PositionActivity.CATEGORY,category)
                intent.putExtra(PositionActivity.NAME,it.name)
                startActivity(intent)
            }
            return
        }
        root.child("menu").child(category).child(list.first)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map = dataSnapshot.value as Map<String, *>
                    ans.add(
                        Add_menu_pos(
                            map["name"].toString(),
                            map["link"].toString(),
                            map["price"].toString(),
                            map["weight"].toString()
                        )
                    )
                    list.removeFirst()
                    read(list)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
}
