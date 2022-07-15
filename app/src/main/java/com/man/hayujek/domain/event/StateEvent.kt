package com.man.hayujek.domain.event

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
sealed class StateEvent<T> {
    class Idle<T> : StateEvent<T>()
    class Loading<T> : StateEvent<T>()
    data class Success<T>(val data: T) : StateEvent<T>()
    data class Failure<T>(val exception: Throwable) : StateEvent<T>()
}