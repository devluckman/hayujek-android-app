package com.man.hayujek.presentation.splash

import androidx.lifecycle.ViewModel
import com.man.hayujek.base.BaseViewModel
import com.man.hayujek.data.preferences.CacheStorage
import com.man.hayujek.data.preferences.DataStoreManager
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Scope

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */
@Scope(SplashFragment::class)
class SplashVM(
    private val cacheStorage: CacheStorage
) : BaseViewModel() {

    val isTokenExist = flow<String> {
        emit(cacheStorage.getApiToken())
    }

}