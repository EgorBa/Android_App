package com.example.krut_bar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class About : Fragment() {

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.about, null)
        val btm = v.findViewById<Button>(R.id.buttom)
        val vk = v.findViewById<LinearLayout>(R.id.vk)
        val inst = v.findViewById<LinearLayout>(R.id.insta)
        btm.setOnClickListener {
            val intent = Intent(context, MapsActivity::class.java)
            startActivity(intent)
        }
        vk.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/krutilnyabar"))
            startActivity(browserIntent)
        }
        inst.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/krutilnya/"))
            startActivity(browserIntent)
        }
        return v
    }

}
