package com.man.hayujek.presentation.maps

import androidx.lifecycle.viewModelScope
import com.man.hayujek.base.BaseViewModel
import com.man.hayujek.domain.model.RouteModel
import com.man.hayujek.domain.usecase.maps.UseCaseMaps
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */
@Scope(MapsFragment::class)
class MapsVM(
    private val useCaseMaps: UseCaseMaps
) : BaseViewModel() {


    private val mutableRoute = MutableStateFlow<RouteModel>(RouteModel(listOf()))
    val routes : Flow<RouteModel> = mutableRoute

    fun getRoute() {
       viewModelScope.launch {
           mutableRoute.value = useCaseMaps.getRoute()
       }
    }

}