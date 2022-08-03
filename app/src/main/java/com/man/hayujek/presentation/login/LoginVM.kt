package com.man.hayujek.presentation.login

import androidx.lifecycle.viewModelScope
import com.man.hayujek.base.BaseViewModel
import com.man.hayujek.data.preferences.CacheStorage
import com.man.hayujek.domain.event.StateEventSubscriber
import com.man.hayujek.domain.event.convertEventToSubscriber
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.usecase.customer.UseCaseCustomer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
@Scope(LoginFragment::class)
class LoginVM(
    private val useCaseCustomer: UseCaseCustomer,
    private val cacheStorage: CacheStorage
) : BaseViewModel() {

    // region API & State

    private val loginManager = useCaseCustomer.loginStateEvent

    private val scope = useCaseCustomer.loginStateEvent.createScope(viewModelScope)

    fun subscribeLogin(subscriber: StateEventSubscriber<String>) {
        convertEventToSubscriber(loginManager, subscriber)
    }

    fun postLogin(requestLogin: RequestLogin) = scope.launch {
        useCaseCustomer.loginCustomer(requestLogin)
    }

    fun setToken(token: String){
        cacheStorage.setApiToken(token)
    }

    // endregion

    // region Validation

    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    fun setUserName(name: String) {
        _username.value = name
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_username, _password) { username, password ->
        val isNameCorrect = username.isNotEmpty()
        val isPasswordCorrect = password.isNotEmpty()
        return@combine isNameCorrect and isPasswordCorrect
    }

    // endregion


}