package com.example.latihanmaps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.latihanmaps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Lokasi hanya diambil saat tombol diklik
        binding.btnMyLocation.setOnClickListener {
            getMyLastLocation()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true

        // Posisi awal (tidak langsung ke lokasi user)
        val indonesia = LatLng(-2.5489, 118.0149)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 4f))

        // Klik manual di map
        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Lokasi Dipilih")
            )
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 18f)
            )
            getAddressFromLatLng(latLng)
        }
    }

    // ===== MENU =====
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.maps_normal -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.maps_satellite -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.maps_terrain -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
        return true
    }

    // ===== PERMISSION =====
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getMyLastLocation()
            } else {
                Toast.makeText(this, "Izin lokasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getMyLastLocation() {
        val fineGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    showMyLocation(location)
                }
            }
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun showMyLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)

        mMap.clear()
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Posisi Saya")
        )

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 18f)
        )

        getAddressFromLocation(location)
    }

    // ===== GEOCODER =====
    private fun getAddressFromLocation(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        if (!addresses.isNullOrEmpty()) {
            binding.textAddress.text = addresses[0].getAddressLine(0)
        }
    }

    private fun getAddressFromLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )
        if (!addresses.isNullOrEmpty()) {
            binding.textAddress.text = addresses[0].getAddressLine(0)
        }
    }
}
