package com.unix14.android.wheretosea.features.map

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.unix14.android.wheretosea.R
import com.unix14.android.wheretosea.common.PermissionsManager
import kotlinx.android.synthetic.main.activity_map.*






class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private val serverLocations = ArrayList<LatLng>()
    private lateinit var dbRef: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val DEFAULT_ZOOM: Float = 150f
    private var mLastKnownLocation: LatLng? = null
    private var mLocationPermissionGranted: Boolean = false
    private var googleMap: GoogleMap?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)





        if(PermissionsManager.canAccessLocation(applicationContext)){
            initMap()
        }else if (!PermissionsManager.getLocationPermission(this)) {
            Toast.makeText(this, "Please allow GPS location permission", Toast.LENGTH_LONG).show()
        }

    }

    private fun initMap() {
        (mapFragment as SupportMapFragment).getMapAsync(this)

        database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("pin_points")

        dbRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, child: String?) {
                child?.let{
                    Log.d("MapActivity", "Value is: $child")
                    Toast.makeText(applicationContext,"changed data from Fb is $child ",Toast.LENGTH_LONG).show()
                    val value:HashMap<*,*> = dataSnapshot.value as HashMap<*, *>

                    val lat = value["lat"]?.let{it as Double}
                    val lng = value["lng"]?.let{it as Double}

                    if(lat != null && lng != null){
                        val newPinPoint = LatLng(lat,lng)
                        serverLocations.add(newPinPoint)

                        googleMap?.addMarker(MarkerOptions().position(newPinPoint))
                    }
                }
            }

        })

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            if(mLocationPermissionGranted){
                it.isMyLocationEnabled = true
                it.uiSettings.isMyLocationButtonEnabled = true
            }
            it.uiSettings.isZoomControlsEnabled = false
            it.uiSettings.isCompassEnabled = true
            it.uiSettings.isRotateGesturesEnabled = true
            it.uiSettings.isZoomGesturesEnabled = true

            it.setOnMapClickListener { tappedPosition->
                googleMap?.addMarker(MarkerOptions().position(tappedPosition))
            }

            it.setOnInfoWindowClickListener(this)



            for (location in serverLocations){
                googleMap?.addMarker(MarkerOptions().position(location))
            }
            it.setOnMarkerClickListener { marker ->

                var ref = dbRef.push()
                ref.child("lat").setValue(marker.position.latitude)
                ref.child("lng").setValue(marker.position.longitude)

                marker.title = "Kaki"
                marker.showInfoWindow()
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beach))
                marker.snippet = "Sexxxxxxxxx"
                true
            }
//             Add a marker in Sydney, Australia, and move the camera.
//            val sydney = LatLng(-34.0, 151.0)
//            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            it.moveCamera(CameraUpdateFactory.newLatLng(LatLng(31.771959,35.217018)))

            updateLocationUI()
        }

    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.let{
            if(it.isVisible){
                it.showInfoWindow()
            }else{
                it.hideInfoWindow()
            }

        }
    }



    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true

                    googleMap?.let{
                        it.isMyLocationEnabled = true
                        it.uiSettings.isMyLocationButtonEnabled = true
                    }
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        googleMap?.let{
            try {
                if (mLocationPermissionGranted) {
                    it.isMyLocationEnabled = true
                    it.uiSettings.isMyLocationButtonEnabled = true
                } else {
                    it.isMyLocationEnabled = false
                    it.uiSettings.isMyLocationButtonEnabled = false
                    mLastKnownLocation = null
                    PermissionsManager.getLocationPermission(this)
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message)
            }
        }

    }
}
