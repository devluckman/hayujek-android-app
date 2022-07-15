package com.man.hayujek.domain.usecase.driver

import com.man.hayujek.data.repository.Repository
import com.man.hayujek.domain.event.StateEventManager
import com.man.hayujek.domain.event.default
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister

/**
 *
 * Created by Lukmanul Hakim on  15/07/22
 * devs.lukman@gmail.com
 */
class UseCaseDriverImpl(
    private val repository: Repository
) : UseCaseDriver {

    // region GET Profile

    private val _userStateEvent = default<UserModel>()
    override val userStateEvent: StateEventManager<UserModel>
        get() = _userStateEvent

    override suspend fun getProfileCustomer() {
        repository.getProfileDriver().collect(_userStateEvent)
    }

    // endregion

    // region POST Login

    private val _loginStateEvent = default<String>()
    override val loginStateEvent: StateEventManager<String>
        get() = _loginStateEvent

    override suspend fun loginCustomer(request: RequestLogin) {
        repository.loginDriver(request).collect(_loginStateEvent)
    }

    // endregion

    // region POST Register

    private val _registerStateEvent = default<Boolean>()
    override val registerStateEvent: StateEventManager<Boolean>
        get() = _registerStateEvent

    override suspend fun registerCustomer(request: RequestRegister) {
        repository.registerDriver(request).collect(_registerStateEvent)
    }

    // endregion
}