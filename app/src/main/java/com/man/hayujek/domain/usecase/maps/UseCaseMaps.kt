package com.man.hayujek.domain.usecase.maps

import com.man.hayujek.domain.model.RouteModel

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
interface UseCaseMaps {

    suspend fun getRoute() : RouteModel


}