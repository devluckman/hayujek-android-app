package com.man.hayujek.presentation.register

import androidx.lifecycle.viewModelScope
import com.man.hayujek.base.BaseViewModel
import com.man.hayujek.domain.event.StateEventSubscriber
import com.man.hayujek.domain.event.convertEventToSubscriber
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.domain.request.RequestRegister
import com.man.hayujek.domain.usecase.customer.UseCaseCustomer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

/**
 *
 * Created by Lukmanul Hakim on  15/07/22
 * devs.lukman@gmail.com
 */
@Scope(RegisterFragment::class)
class RegisterVM(
    private val useCase: UseCaseCustomer
) : BaseViewModel() {

    // region API & State

    private val registerManager = useCase.registerStateEvent

    private val scope = useCase.registerStateEvent.createScope(viewModelScope)

    fun subscribeRegister(subscriber: StateEventSubscriber<Boolean>) {
        convertEventToSubscriber(registerManager, subscriber)
    }

    fun postRegister(requestLogin: RequestRegister) = scope.launch {
        useCase.registerCustomer(requestLogin)
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