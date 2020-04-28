package com.example.krutbarmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var root: DatabaseReference = FirebaseDatabase.getInstance().reference.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find.setOnClickListener {
            root.child("code").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<*, *>
                    if (map.containsKey(code.text.toString())) {
                        val id = map[code.text.toString()].toString()
                        root.child("users").child(id).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                map = p0.value as Map<*, *>
                                val point = map["points"].toString()
                                sum.text = point + " ${getPoints(point.toInt())} у пользователя"
                            }
                        })
                    }
                }

            })
        }
        plus.setOnClickListener {
            root.child("code").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<*, *>
                    val id = map[code.text.toString()].toString()
                    root.child("users").child(id).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            map = p0.value as Map<*, *>
                            val point = map["points"].toString().toInt()
                            val add = points.text.toString().toInt()
                            root.child("users").child(id).child("points").setValue(point + add)
                            Toast
                                .makeText(
                                    this@MainActivity,
                                    "Начислено $add ${getPoints(add)}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    })
                }

            })
        }
        minus.setOnClickListener {
            root.child("code").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<*, *>
                    val id = map[code.text.toString()].toString()
                    root.child("users").child(id).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            map = p0.value as Map<*, *>
                            val point = map["points"].toString().toInt()
                            val minus = points.text.toString().toInt()
                            root.child("users").child(id).child("points").setValue(point - minus)
                            Toast
                                .makeText(
                                    this@MainActivity,
                                    "Списано $minus ${getPoints(minus)}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    })
                }

            })
        }
    }

    private fun getPoints(point: Int): String? {
        val a = point % 10
        if (a == 0 || a >= 6) {
            return "баллов"
        }
        return if (a == 1) {
            "балл"
        } else {
            "балла"
        }
    }
}
