package com.example.krut_bar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_position.*
import kotlinx.android.synthetic.main.activity_position.view.*

class PositionActivity : AppCompatActivity() {

    companion object{
        const val NAME = "name"
        const val CATEGORY = "category"
    }

    private var root: DatabaseReference = FirebaseDatabase.getInstance().reference.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position)
        read()
    }

    private fun read() {
        val category = intent.getStringExtra(CATEGORY)
        val name = intent.getStringExtra(NAME)
        root.child("menu").child(category).child(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map = dataSnapshot.value as Map<*, *>
                    nameOf.text = map["name"].toString()
                    try {
                        Picasso.get()
                            .load(map["link"].toString())
                            .error(R.drawable.ic_close_black_24dp)
                            .into(image.image)
                    } catch (e: Exception) {
                    }
                    try {
                        description.text = map["description"].toString()
                    } catch (e: Exception) {
                    }
                    weight.text = map["weight"].toString()
                    price.text = map["price"].toString()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
}
