package com.example.travelapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.type.LatLng
import java.util.Locale

class SelectMapActivity : AppCompatActivity(), OnMapReadyCallback {
    var location: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_map)
        val preferences = getSharedPreferences("myPrefs", MODE_PRIVATE)

        location = preferences.getString("location", "")
        val mapFragment: SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val lat = 0.0
        val lng = 0.0
        val appointLoc = com.google.android.gms.maps.model.LatLng(lat, lng)
        googleMap.addMarker(MarkerOptions().position(
            com.google.android.gms.maps.model.LatLng(
                lat,
                lng
            )
        ).title("Marker"))
        val geocoder = Geocoder(this@SelectMapActivity, Locale.getDefault())
        try {
            val listAddress = geocoder.getFromLocationName(
                location!!, 1
            )
            if (listAddress!!.size > 0) {
                val latLng = com.google.android.gms.maps.model.LatLng(
                    listAddress[0].latitude,
                    listAddress[0].longitude
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.addMarker(MarkerOptions().position(latLng).title("input"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        googleMap.getUiSettings().setZoomControlsEnabled(true)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@SelectMapActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            ActivityCompat.requestPermissions(
                this@SelectMapActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
            return
        }
        googleMap.setMyLocationEnabled(true)
    }
}