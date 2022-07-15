package com.man.hayujek.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.man.hayujek.base.BaseViewModel
import com.man.hayujek.data.preferences.CacheStorage
import com.man.hayujek.domain.event.StateEventSubscriber
import com.man.hayujek.domain.event.convertEventToSubscriber
import com.man.hayujek.domain.model.UserModel
import com.man.hayujek.domain.usecase.customer.UseCaseCustomer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

/**
 *
 * Created by Lukmanul Hakim on  15/07/22
 * devs.lukman@gmail.com
 */
@Scope(HomeFragment::class)
class HomeVM(
    private val useCase: UseCaseCustomer,
    private val cacheStorage: CacheStorage
) : BaseViewModel() {

    // region API & State

    private val userManager = useCase.userStateEvent

    private val scope = useCase.userStateEvent.createScope(viewModelScope)

    fun subscribeUser(subscriber: StateEventSubscriber<UserModel>) {
        convertEventToSubscriber(userManager, subscriber)
    }

    fun getProfile() = scope.launch {
        useCase.getProfileCustomer()
    }

    fun logout(){
        cacheStorage.clearToken()
    }

    // endregion


}