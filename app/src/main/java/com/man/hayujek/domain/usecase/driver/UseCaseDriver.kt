package com.man.hayujek.domain.usecase.driver

import com.man.hayujek.domain.event.StateEventManager
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister

/**
 *
 * Created by Lukmanul Hakim on  15/07/22
 * devs.lukman@gmail.com
 */
interface UseCaseDriver {

    val userStateEvent: StateEventManager<UserModel>
    suspend fun getProfileCustomer()

    val loginStateEvent: StateEventManager<String>
    suspend fun loginCustomer(request: RequestLogin)

    val registerStateEvent: StateEventManager<Boolean>
    suspend fun registerCustomer(request: RequestRegister)

}