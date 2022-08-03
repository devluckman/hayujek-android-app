package com.man.hayujek.presentation.maps

import android.Manifest
import android.graphics.Color
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.man.hayujek.R
import com.man.hayujek.base.BaseFragment
import com.man.hayujek.databinding.FragmentMapsBinding
import com.man.hayujek.domain.model.RouteModel
import com.man.hayujek.extentions.isPermissionGranted
import com.man.hayujek.location.LocationManager
import com.man.hayujek.location.MarkerManager
import com.man.hayujek.utils.log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment : BaseFragment<MapsVM, FragmentMapsBinding>(FragmentMapsBinding::inflate),
    OnMapReadyCallback {

    override val viewModel: MapsVM by viewModel()
    private val locationManager: LocationManager by lazy {
        LocationManager(requireContext())
    }

    private lateinit var mMap: GoogleMap
    private lateinit var marker: Marker

    override fun initProcess() {
        initMaps()
        initLocation()
        initObserver()
    }

    private fun initMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initLocation() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        if (isPermissionGranted(locationPermission)) {
            MainScope().launch {
                locationManager.getLocation().collect(onLocationResult())
            }
        } else requestPermissionLocation.launch(locationPermission)
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.routes.collect(::drawRoute)
        }
    }

    // region Permission

    private val requestPermissionLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            MainScope().launch {
                locationManager.getLocation().collect(onLocationResult())
            }
        } else {
            showMessage("This app need location permission")
        }
    }

    // endregion

    private fun onLocationResult() = FlowCollector<Location> { location ->
        log("location result -> ${location.latitude} - ${location.longitude}")
        val newLatLng = LatLng(location.latitude, location.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 17f))

        if (!this::marker.isInitialized) {
            val markerOption = MarkerOptions()
                .position(newLatLng)
            mMap.addMarker(markerOption)?.let {
                marker = it
            }
        } else {
            //MarkerManager.moveMarkerSmoothly(marker, newLatLng)
            MarkerManager.animateMarkerNew(
                startPosition = marker.position,
                destination = newLatLng,
                marker = marker,
                googleMap = mMap
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true

        viewModel.getRoute()
    }

    private fun drawRoute(route: RouteModel) {
        if (route.data.isNotEmpty()){
            val coordinates = route.data
                .map {
                    LatLng(it.latitude ?: 0.0, it.longitude ?: 0.0)
                }

            val point1 = coordinates.firstOrNull()
            point1?.let {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 14f))
            }

            val polyline = PolylineOptions()
                .addAll(coordinates)
                .width(6f)
                .color(Color.RED)
            mMap.addPolyline(polyline)
        }
    }

}