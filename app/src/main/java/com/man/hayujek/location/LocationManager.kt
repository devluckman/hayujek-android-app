package com.man.hayujek.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
@SuppressLint("MissingPermission")
class LocationManager(private val context: Context) {

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }
    }

    private val distanceValidator = 30f

    private val fusedProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    suspend fun getLocation(): Flow<Location> {
        val callbackFlow = callbackFlow<Location> {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    for (location in result.locations) {
                        trySend(location).isSuccess
                    }
                }
            }

            fusedProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            ).addOnCanceledListener {
                cancel("Cancel request", Throwable("Canceled by user"))
            }.addOnFailureListener {
                cancel("Failure get location", it)
            }

            awaitClose { fusedProviderClient.removeLocationUpdates(locationCallback) }
        }

        return callbackFlow.distinctUntilChanged { old, new ->
            old.distanceTo(new) < distanceValidator
        }
    }

    fun getCurrentLocation(location: (Location) -> Unit) {
        val request = LastLocationRequest.Builder().build()
        fusedProviderClient.getLastLocation(request)
            .addOnFailureListener {
                it.printStackTrace()
            }
            .addOnSuccessListener {
                location(it)
            }
    }
}