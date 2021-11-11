package com.example.demoapptask

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demoapptask.databinding.ActivityMainBinding
import com.example.demoapptask.response.Data
import com.example.demoapptask.vm.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException

import java.util.*
import java.lang.StringBuilder
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.listener.PermissionDeniedResponse

import com.karumi.dexter.listener.PermissionGrantedResponse

import com.karumi.dexter.listener.single.PermissionListener

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyZXN1bHQiOnsiaWQiOjI5LCJlbWFpbCI6ImJlYmFtYXJrZXRwbGFjZUB5b3BtYWlsLmNvbSIsIm5hbWUiOiJCZWJhIE1hcmtldHBsYWNlIiwibW9iaWxlIjoiKzkxMTIzNDU2Nzg5MCIsImlzRGVsZXRlZCI6MCwiY3JlYXRlZER0bSI6IjIwMjEtMTAtMjhUMTA6MTM6MTMuMDAwWiIsInVwZGF0ZWREdG0iOiIyMDIxLTEwLTI4VDEwOjEzOjEzLjAwMFoiLCJkZXZpY2VJZCI6IjE4RDFFMTA3LUEwRjYtNDdFOC04NzU3LUNEMzhEMzk3MTI0RSIsInBsYXRmb3JtIjoiMCIsInVzZXJJbWciOiJ1cGxvYWRzL1VzZXJQcm9maWxlL3VzZXIuanBnIiwiZGV2aWNlVHlwZSI6IklPUyIsImdtYWlsIjoiMCIsImZhY2Vib29rIjoiMCIsImxhdGl0dWRlIjoiIiwibG9uZ2l0dWRlIjoiIiwidG9rZW4iOiJkVmdUWGplQTIwVDhsVUFJTklMNVlROkFQQTkxYkZnSjh4TlgtSER6QXo0MXFNMHNjcGJUaXRvMzZtRjFXcFFvRmdDQzlQUVpwYkY1MklsMHVNOWc2Rm4xZksxOHlSMndWQzlRTFZ5RFhZZlFWd0QxTzVFcmgyQVlmV25xenNlbWxRQy1KVF9BYUlqbUlKcVViSHNZUVFOalpTcXN5SWxVWldJIiwiaXNBY3RpdmUiOjF9LCJpYXQiOjE2MzY0MzgxNDIsImV4cCI6MTYzOTAzMDE0Mn0.Zix-gF_JUM7sMO67JGRvzkx0JZL4nMng0fBOBZttkuY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

    }

    fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel.getRecylerDataObserver().observe(this, Observer<List<Data>?> {
            if (it != null) {
                binding.recyclerView.apply {
                    val layoutManager = GridLayoutManager(this@MainActivity, 2)
                    this.layoutManager = layoutManager
                    val adapterClass = AdapterClass(this@MainActivity, it)
                    this.adapter = adapterClass
                }
            } else {
                Toast.makeText(this, "Some error from api", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.callApi(token)
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    obtieneLocalizacion()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }
            }).check()

    }

    @SuppressLint("MissingPermission")
    private fun obtieneLocalizacion() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                getAddressFromLocation(location!!.latitude, location.longitude)
            }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.ENGLISH)
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val fetchedAddress: Address = addresses[0]
                val strAddress = StringBuilder()

                strAddress.append(fetchedAddress.getAddressLine(0)).append(" ")
                val address =
                    addresses[0].getAddressLine(0)
                binding.txtLocation.setText(address)
            } else {
                binding.txtLocation.setText("Searching Current Address")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("varun", e.message.toString())

        }
    }

}