package com.man.hayujek.data.repository

import com.man.hayujek.data.preferences.CacheStorage
import com.man.hayujek.data.source.NetworkDataSource
import com.man.hayujek.domain.event.FlowState
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister
import org.koin.core.annotation.Single

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
@Single
class RepositoryImpl(
    private val network: NetworkDataSource,
    private val cacheStorage: CacheStorage
) : Repository {

    // region Customer

    override suspend fun loginCustomer(request: RequestLogin): FlowState<String> {
        return network.loginCustomer(request)
    }

    override suspend fun registerCustomer(request: RequestRegister): FlowState<Boolean> {
        return network.registerCustomer(request)
    }

    override suspend fun getProfileCustomer(): FlowState<UserModel> {
        val token: String = cacheStorage.getApiToken()
        return network.getProfileCustomer(token = token)
    }



    // endregion

    // region Driver

    override suspend fun loginDriver(request: RequestLogin): FlowState<String> {
        return network.loginDriver(request)
    }

    override suspend fun registerDriver(request: RequestRegister): FlowState<Boolean> {
        return network.registerDriver(request)
    }

    override suspend fun getProfileDriver(): FlowState<UserModel> {
        val token: String = cacheStorage.getApiToken()
        return network.getProfileDriver(token = token)
    }

    // endregion
}