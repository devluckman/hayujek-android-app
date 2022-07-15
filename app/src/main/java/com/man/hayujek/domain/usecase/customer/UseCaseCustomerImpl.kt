package com.man.hayujek.domain.usecase.customer

import com.man.hayujek.data.repository.Repository
import com.man.hayujek.domain.event.StateEventManager
import com.man.hayujek.domain.event.default
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister
import org.koin.core.annotation.Factory

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
@Factory
class UseCaseCustomerImpl(
    private val repository: Repository
) : UseCaseCustomer {

    // region GET Profile

    private val _userStateEvent = default<UserModel>()
    override val userStateEvent: StateEventManager<UserModel>
        get() = _userStateEvent

    override suspend fun getProfileCustomer() {
        repository.getProfileCustomer().collect(_userStateEvent)
    }

    // endregion

    // region POST Login

    private val _loginStateEvent = default<String>()
    override val loginStateEvent: StateEventManager<String>
        get() = _loginStateEvent

    override suspend fun loginCustomer(request: RequestLogin) {
        repository.loginCustomer(request).collect(_loginStateEvent)
    }

    // endregion

    // region POST Register

    private val _registerStateEvent = default<Boolean>()
    override val registerStateEvent: StateEventManager<Boolean>
        get() = _registerStateEvent

    override suspend fun registerCustomer(request: RequestRegister) {
        repository.registerCustomer(request).collect(_registerStateEvent)
    }

    // endregion

}