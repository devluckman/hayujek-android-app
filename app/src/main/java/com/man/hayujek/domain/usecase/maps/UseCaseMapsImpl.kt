package com.man.hayujek.domain.usecase.maps

import com.man.hayujek.data.repository.Repository
import com.man.hayujek.domain.model.RouteModel
import org.koin.core.annotation.Factory

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
@Factory
class UseCaseMapsImpl(
    private val repository: Repository
) : UseCaseMaps {

    override suspend fun getRoute(): RouteModel = repository.getRoute()


}