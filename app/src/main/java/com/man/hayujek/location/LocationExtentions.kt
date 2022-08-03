package com.man.hayujek.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng

/**
 *
 * Created by Lukmanul Hakim on  03/08/22
 * devs.lukman@gmail.com
 */

fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)

fun LatLng.toLocation(): Location = Location("").also {
    it.latitude = latitude
    it.longitude = longitude
}