package com.man.hayujek.data.repository

import com.man.hayujek.domain.event.FlowState
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
interface Repository {

    suspend fun loginCustomer(request: RequestLogin): FlowState<String>

    suspend fun registerCustomer(request: RequestRegister): FlowState<Boolean>

    suspend fun getProfileCustomer(): FlowState<UserModel>


    suspend fun loginDriver(request: RequestLogin): FlowState<String>

    suspend fun registerDriver(request: RequestRegister): FlowState<Boolean>

    suspend fun getProfileDriver(): FlowState<UserModel>

}