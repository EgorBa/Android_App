package com.example.myapplicationkotlin

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri.fromParts
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View


class MainActivity : AppCompatActivity() {

    private val myRequestId = 10
    var contacts = emptySet<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                myRequestId
            )
        } else {
            run()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            myRequestId -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    run()
                } else {
                    Toast
                        .makeText(
                            this,
                            "Don't work with out your permission",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                return
            }
        }
    }

    fun run (){
        contacts = fetchAllContacts()
        send(contacts)
        inputSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val ans: MutableSet<Contact> = filtration(contacts, p0)
                if (ans.isEmpty()) {
                    sad.visibility = View.VISIBLE
                } else {
                    sad.visibility = View.INVISIBLE
                }
                send(ans)
            }
        })
    }

    fun send(set: Set<Contact>) {
        val ans: MutableList<Contact> = mutableListOf()
        for (i in set) {
            ans.add(i)
        }
        my_recycler_view.adapter = UserAdapter(ans) {
            Toast
                .makeText(
                    this@MainActivity,
                    "Clicked on user ${it.name}",
                    Toast.LENGTH_SHORT
                )
                .show()
            val phoneIntent = Intent(
                Intent.ACTION_DIAL, fromParts(
                    "tel", it.phoneNumber, null
                )
            )
            startActivity(phoneIntent)
        }
    }

}

fun filtration(contacts: Set<Contact>, charSequence: CharSequence?): MutableSet<Contact> {
    var ans: MutableSet<Contact> = mutableSetOf()
    if (charSequence == null) {
        ans = contacts as MutableSet<Contact>
    } else {
        for (i in contacts) {
            for (k in i.name.indices) {
                var j = k
                var j1 = 0
                var count = 0
                while (j1 < charSequence.length && j < i.name.length && i.name[j].toLowerCase() == charSequence[j1].toLowerCase()) {
                    count++
                    j++
                    j1++
                }
                if (count == charSequence.length) {
                    ans.add(i)
                    break
                }
            }
            for (k in i.phoneNumber.indices) {
                var j = k
                var j1 = 0
                var count = 0
                while (j1 < charSequence.length && j < i.phoneNumber.length && i.phoneNumber[j] == charSequence[j1]) {
                    count++
                    j++
                    j1++
                }
                if (count == charSequence.length) {
                    ans.add(i)
                    break
                }
            }
        }
    }
    return ans
}

