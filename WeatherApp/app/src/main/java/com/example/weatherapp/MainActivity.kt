package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
//import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val ID = "498817"
    val DAYS = "7"
    val KEY = BuildConfig.API_KEY
    private val myRequestId = 10
    private var db: AppDatabase? = null
    private var weekDao: WeekDao? = null
    var week: Week? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = ProgressBar.VISIBLE
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                myRequestId
            )
        } else {
            db = AppDatabase.getAppDataBase(context = this)
        }
        if (savedInstanceState == null) {
            run()
        } else {
            week = savedInstanceState.getParcelable("Weather")
            week?.let { parse(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("Weather", week)
    }

    private var call: Call<Week> ?= null

    private fun run() {
        call = MyApp.app.weatherAPI.getWeather(ID, DAYS, KEY)
        call?.enqueue(object : Callback<Week> {

            override fun onFailure(call: Call<Week>, t: Throwable) {
                doAsync {
                    weekDao = db?.weekDao()
                    val body = weekDao?.getById(0)
                    uiThread {
                        if (body == null) {
                            Toast.makeText(this@MainActivity, "Turn on Internet", Toast.LENGTH_LONG)
                                .show()
                            progressBar.visibility = ProgressBar.INVISIBLE
                        } else {
                            parse(body)
                        }
                    }
                }
            }

            override fun onResponse(call: Call<Week>, response: Response<Week>) {
                val body = response.body() ?: return
                doAsync {
                    weekDao = db?.weekDao()
                    weekDao?.update(body)
                    uiThread {
                    }
                }
                parse(body)
            }
        })
    }

    fun sign(a: String): String {
        val curTemp = a.toDouble()
        var sign = ""
        if (curTemp > 0) {
            sign = "+ "
        }
        return sign
    }

    @SuppressLint("SetTextI18n")
    fun parse(body : Week) {
        week = body
        val today = body.data[0]
        var ans = today.datetime.split('-')
        date.text = ans[2] + "." + ans[1] + "." + ans[0]
        description.text = today.weather.description
        val sign = sign(today.temp)
        temp.text = sign + today.temp + " C"
        city.text = body.cityName
        text1.text = today.windSpd + " m/s"
        text3.text = today.pres + " mb"
        val daysIcon = listOf(i1, i2, i3, i4, i5, i6)
        val daysTemp = listOf(i1down, i2down, i3down, i4down, i5down, i6down)
        val daysDate = listOf(i1up, i2up, i3up, i4up, i5up, i6up)
        Picasso.get()
            .load("https://www.weatherbit.io/static/img/icons/" + today.weather.icon + ".png")
            .tag(MainActivity::class.java)
            .error(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
            .into(mainImage)
        for (i in (1..6)) {
            Picasso.get()
                .load("https://www.weatherbit.io/static/img/icons/" + body.data[i].weather.icon + ".png")
                .tag(MainActivity::class.java)
                .error(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                .into(daysIcon[i - 1])
            daysTemp[i - 1].text = sign(body.data[i].temp) + body.data[i].temp + " C"
            ans = body.data[i].datetime.split('-')
            daysDate[i - 1].text = ans[2] + "." + ans[1]
        }
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        Picasso.get().cancelTag(MainActivity::class.java)
        call?.cancel()
        call = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            myRequestId -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    db = AppDatabase.getAppDataBase(context = this)
                } else {
                    Toast
                        .makeText(
                            this,
                            "Don't save with out your permission",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                return
            }
        }
    }

}
