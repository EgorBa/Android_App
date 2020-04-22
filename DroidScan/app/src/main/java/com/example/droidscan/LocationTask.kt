package com.example.droidscan

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class LocationTask : Service() {

    private var locationManager: LocationManager? = null
    private val LOCATION_INTERVAL = 0L
    private val LOCATION_DISTANCE = 0f

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        Toast.makeText(
            this, R.string.startLocation,
            Toast.LENGTH_SHORT
        ).show()
        initializeLocationManager()
        requestUpdates(LocationManager.GPS_PROVIDER,0)
        requestUpdates(LocationManager.NETWORK_PROVIDER,1)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val file = File(this.getExternalFilesDir(null).toString(), getString(R.string.fileName))
        if (!file.exists()) {
            file.createNewFile()
        }
        val bw = BufferedWriter(FileWriter(file.toString(),true))
        requestUpdates(LocationManager.GPS_PROVIDER,0)
        requestUpdates(LocationManager.NETWORK_PROVIDER,1)
        bw.write(locationListeners[0].lastLocation.toString() + "\n")
        bw.write(locationListeners[1].lastLocation.toString() + "\n")
        bw.flush()
        bw.close()
        return START_STICKY
    }

    private fun requestUpdates(provider: String, i: Int) {
        try {
            locationManager?.requestLocationUpdates(
                provider, LOCATION_INTERVAL, LOCATION_DISTANCE,
                locationListeners[i]
            )
        } catch (ex: SecurityException) {
            Toast.makeText(
                this, R.string.errorRequest,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(
            this, R.string.stopLocation,
            Toast.LENGTH_SHORT
        ).show()
    }

    private class LocationListener(provider: String) :
        android.location.LocationListener {
        var lastLocation: Location

        override fun onProviderDisabled(provider: String) {
        }

        override fun onProviderEnabled(provider: String) {
        }

        override fun onLocationChanged(location: Location) {
            lastLocation = location
        }

        override fun onStatusChanged(
            provider: String,
            status: Int,
            extras: Bundle
        ) {
        }

        init {
            lastLocation = Location(provider)
        }
    }

    private var locationListeners = arrayOf(
        LocationListener(LocationManager.GPS_PROVIDER),
        LocationListener(LocationManager.NETWORK_PROVIDER)
    )

    private fun initializeLocationManager() {
        if (locationManager == null) {
            locationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

}
