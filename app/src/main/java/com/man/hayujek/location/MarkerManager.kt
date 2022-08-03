package com.man.hayujek.location

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.sign


/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
object MarkerManager {

    fun moveMarkerSmoothly(marker: Marker, newLatLng: LatLng) {
        val animator = ValueAnimator.ofFloat(0f, 100f)
        val deltaLat = doubleArrayOf(newLatLng.latitude - marker.position.latitude)
        val deltaLon = newLatLng.longitude - marker.position.longitude
        val prevStep = floatArrayOf(0f)
        animator.duration = 1500

        animator.addUpdateListener { animation ->
            val deltaStep = (animation.animatedValue as Float - prevStep[0]).toDouble()
            prevStep[0] = animation.animatedValue as Float

            val updatedLat = marker.position.latitude + deltaLat[0] * deltaStep * 1.0 / 100
            val updatedLon = marker.position.longitude + deltaLon * deltaStep * 1.0 / 100

            val updatedLatLng = LatLng(updatedLat, updatedLon)
            marker.position = updatedLatLng
        }

        animator.start()
    }

    private fun getBearing(begin: LatLng, end: LatLng): Float {
        val lat = abs(begin.latitude - end.latitude)
        val lng = abs(begin.longitude - end.longitude)
        if (begin.latitude < end.latitude && begin.longitude < end.longitude) return Math.toDegrees(
            atan(lng / lat)
        )
            .toFloat() else if (begin.latitude >= end.latitude && begin.longitude < end.longitude) return (90 - Math.toDegrees(
            atan(lng / lat)
        ) + 90).toFloat() else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude) return (Math.toDegrees(
            atan(lng / lat)
        ) + 180).toFloat() else if (begin.latitude < end.latitude && begin.longitude >= end.longitude) return (90 - Math.toDegrees(
            atan(lng / lat)
        ) + 270).toFloat()
        return (-1).toFloat()
    }

    fun animateMarkerNew(startPosition: LatLng, destination: LatLng, marker: Marker?, googleMap: GoogleMap) {
        if (marker != null) {
            val endPosition = LatLng(destination.latitude, destination.longitude)
            val startRotation = marker.rotation
            val latLngInterpolator: LatLngInterpolatorNew = LatLngInterpolatorNew.LinearFixed()
            val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
            valueAnimator.duration = 2000 // duration 3 second
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.addUpdateListener { animation ->
                try {
                    val v = animation.animatedFraction
                    latLngInterpolator.interpolate(v, startPosition, endPosition)
                        ?.let { newPosition ->
                            marker.position = newPosition
                            googleMap.moveCamera(
                                CameraUpdateFactory.newCameraPosition(
                                    CameraPosition.Builder()
                                        .target(newPosition)
                                        .zoom(18f)
                                        .build()
                                )
                            )
                            marker.rotation =
                                getBearing(
                                    startPosition,
                                    LatLng(destination.latitude, destination.longitude)
                                )
                        }
                } catch (ex: Exception) {
                    //I don't care atm..
                }
            }
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                    // if (mMarker != null) {
                    // mMarker.remove();
                    // }
                    // mMarker = googleMap.addMarker(new MarkerOptions().position(endPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car)));
                }
            })
            valueAnimator.start()
        }
    }

    private interface LatLngInterpolatorNew {
        fun interpolate(fraction: Float, a: LatLng?, b: LatLng?): LatLng?
        class LinearFixed : LatLngInterpolatorNew {
            override fun interpolate(fraction: Float, a: LatLng?, b: LatLng?): LatLng? {
                if (a != null && b != null) {
                    val lat = (b.latitude - a.latitude) * fraction + a.latitude
                    var lngDelta = b.longitude - a.longitude
                    // Take the shortest path across the 180th meridian.
                    if (abs(lngDelta) > 180) {
                        lngDelta -= sign(lngDelta) * 360
                    }
                    val lng = lngDelta * fraction + a.longitude
                    return LatLng(lat, lng)
                }
                return LatLng(0.0, 0.0)
            }
        }
    }

}