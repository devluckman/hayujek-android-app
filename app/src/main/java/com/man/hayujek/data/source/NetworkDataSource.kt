package com.man.hayujek.data.source

import com.man.hayujek.data.mapper.Mapper
import com.man.hayujek.data.network.ApiServices
import com.man.hayujek.data.network.ApiServicesProvider
import com.man.hayujek.data.response.LoginResponse
import com.man.hayujek.data.response.RegisterResponse
import com.man.hayujek.domain.event.FlowState
import com.man.hayujek.domain.event.asFlowStateEvent
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister
import org.koin.core.annotation.Single
import retrofit2.Response
import retrofit2.http.Body

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
@Single
class NetworkDataSource(
    private val api: ApiServicesProvider
) {

    // region Customer

    suspend fun loginCustomer(request: RequestLogin): FlowState<String> {
        return api.get().loginCustomer(request).asFlowStateEvent {
            Mapper.mapLogin(it)
        }
    }

    suspend fun registerCustomer(request: RequestRegister): FlowState<Boolean> {
        return api.get().registerCustomer(request).asFlowStateEvent {
            Mapper.mapRegister(it)
        }
    }
    suspend fun getProfileCustomer(token: String): FlowState<UserModel> {
        return api.get().getProfileCustomer(token).asFlowStateEvent {
            Mapper.mapUserModel(it)
        }
    }

    // endregion

    // region Driver

    suspend fun getProfileDriver(token: String): FlowState<UserModel> {
        return api.get().getProfileDriver(token).asFlowStateEvent {
            Mapper.mapUserModel(it)
        }
    }

    suspend fun loginDriver(request: RequestLogin): FlowState<String> {
        return api.get().loginDriver(request).asFlowStateEvent {
            Mapper.mapLogin(it)
        }
    }

    suspend fun registerDriver(request: RequestRegister): FlowState<Boolean> {
        return api.get().registerDriver(request).asFlowStateEvent {
            Mapper.mapRegister(it)
        }
    }

    // endregion

}