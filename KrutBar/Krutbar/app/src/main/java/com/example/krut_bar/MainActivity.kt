package com.example.krut_bar

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.background
import kotlinx.android.synthetic.main.activity_main.name
import kotlinx.android.synthetic.main.activity_main.progress
import kotlinx.android.synthetic.main.activity_main.ref
import kotlinx.android.synthetic.main.profile.*


class MainActivity : AppCompatActivity() {

    private var id: String ?= null
    private var root: DatabaseReference = FirebaseDatabase.getInstance().reference.root
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        bottomBar.visibility = View.INVISIBLE
        id = auth.uid
        if (id == null) {
            background.visibility = View.VISIBLE
            name2.visibility = View.VISIBLE
            red_btm.visibility = View.VISIBLE
            reg_name.setOnClickListener {
                if (name.text.toString() == "") {
                    text_hint.text = "Ведите имя"
                } else{
                    name2.visibility = View.INVISIBLE
                    red_btm.visibility = View.INVISIBLE
                    name3.visibility = View.VISIBLE
                    red_btm2.visibility = View.VISIBLE
                    continu.setOnClickListener {
                        if (hasConnection(this)) {
                            registration()
                        } else {
                            text_hint2.text = "Проверьте интернет подключение"
                        }
                    }
                    ready.setOnClickListener {
                        check()
                    }
                }
            }
        } else {
            create_frags()
        }
    }

    private fun check() {
        root.child("users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    text_hint2.text = "Проверьте интернет подключение"
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val map = p0.value as Map<*, *>
                    if (map.keys.contains(ref.text.toString())) {
                        registration()
                    } else {
                        text_hint2.text = "Проверьте правильность введенной ссылки"
                    }
                }

            })
    }

    private fun registration() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    id = auth.uid.toString()
                    root.child("users").child(id.toString()).updateChildren(
                        mapOf(
                            "name" to name.text.toString(),
                            "points" to 50,
                            "friend_link" to ref.text.toString(),
                            "have_bought_status" to false
                        )
                    )
                    name3.visibility = View.INVISIBLE
                    red_btm2.visibility = View.INVISIBLE
                    create_frags()
                } else {
                    text_hint2.text = "Проверьте интернет подключение"
                }
            }
    }

    private fun create_frags(){
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        name2.visibility = View.VISIBLE
        background.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        progress.startAnimation(rotate)
        val menu = Menu()
        val profile = Profile(id)
        val about = About()
        val delivery = Delivery()
        val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.parent_fragment_container,menu,"menu")
        ft.add(R.id.parent_fragment_container,profile,"profile")
        ft.add(R.id.parent_fragment_container,about,"about")
        ft.add(R.id.parent_fragment_container,delivery,"delivery")
        ft.hide(profile)
        ft.hide(menu)
        ft.hide(delivery)
        ft.hide(about)
        ft.show(profile)
        ft.commit()
        tab_center.setOnClickListener{
            val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.hide(menu)
            ft.hide(about)
            ft.hide(delivery)
            ft.show(profile)
            ft.commit()
        }
        tab_left.setOnClickListener{
            val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.hide(profile)
            ft.hide(delivery)
            ft.hide(about)
            ft.show(menu)
            ft.commit()
        }
        tab_right_right.setOnClickListener {
            val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.hide(profile)
            ft.hide(menu)
            ft.hide(delivery)
            ft.show(about)
            ft.commit()
        }
        tab_right.setOnClickListener {
            val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.hide(profile)
            ft.hide(menu)
            ft.hide(about)
            ft.show(delivery)
            ft.commit()
        }
        name2.visibility = View.INVISIBLE
        background.visibility = View.INVISIBLE
        animation.visibility = View.INVISIBLE
        bottomBar.visibility = View.VISIBLE
    }

    fun hasConnection(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }
}

