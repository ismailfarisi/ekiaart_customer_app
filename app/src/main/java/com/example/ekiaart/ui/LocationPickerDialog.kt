package com.example.ekiaart.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.ekiaart.databinding.FragmentLocationPickerDialogBinding
import com.example.ekiaart.util.GPS_REQUEST
import com.example.ekiaart.util.GpsUtils
import com.example.ekiaart.util.TAG
import com.google.android.gms.location.*
import java.util.*


class LocationPickerDialog : DialogFragment() {
    companion object {
        const val FINE_LOCATION_PERMISSION = 11
    }


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var listener: LocationDialogListener
    private lateinit var binding: FragmentLocationPickerDialogBinding
    private var isGps = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listener = targetFragment as LocationDialogListener
        binding = FragmentLocationPickerDialogBinding.inflate(inflater, container, false)
        GpsUtils(requireContext()).turnGPSOn(object : GpsUtils.onGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                isGps = isGPSEnable
            }
        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    Log.d(TAG, "onLocationResult: $location")
                    getAddressFromCoOrdinates(location)
                    if (fusedLocationProviderClient != null) {
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                        Log.d(TAG, "onLocationResult: updates removed ")
                    }
                }

            }
        }
        binding.useCurrentLocationBtn.setOnClickListener {
            updateGPS()
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            FINE_LOCATION_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permission Required to access Location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateGPS() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "updateGPS: permission is granted")
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    getAddressFromCoOrdinates(location)
                    Log.d(TAG, "updateGPS: location is $location")
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                } else {
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null
                    )
                }

            }
        } else {
            Log.d(TAG, "updateGPS: no permission")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_PERMISSION
                )
            }
        }
    }

    private fun getAddressFromCoOrdinates(location: Location) {
        val post = Geocoder(requireContext(), Locale.getDefault())
        val address = post.getFromLocation(location.latitude, location.longitude, 1)
        val postcode = address[address.lastIndex].postalCode
    }

    interface LocationDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    data class LocationData(
        val location: Location,
        val postCode: String
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGps = true; // flag maintain before get location
            }
        }
    }


}