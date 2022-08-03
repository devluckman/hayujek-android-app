package com.man.hayujek.domain.model

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
data class RouteModel(
    val data: List<RouteItem>
) {
    class RouteItem(
        val latitude: Double?,
        val longitude: Double?
    )
}