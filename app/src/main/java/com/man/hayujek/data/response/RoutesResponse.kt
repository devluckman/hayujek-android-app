package com.man.hayujek.data.response

import com.google.gson.annotations.SerializedName
import com.man.hayujek.base.BaseResponse
import com.man.hayujek.domain.model.RouteModel

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
class RoutesResponse : BaseResponse<RoutesResponse.Data>() {
    class Data(
        @SerializedName("route")
        val route: List<Route?>?
    ) {
        data class Route(
            @SerializedName("latitude")
            val latitude: Double?,
            @SerializedName("longitude")
            val longitude: Double?
        )
    }

    companion object {

        fun Data?.toDomain() : List<RouteModel.RouteItem> = this?.route?.map { RouteModel.RouteItem(
            latitude = it?.latitude,
            longitude = it?.longitude
        ) } ?: listOf()

    }
}