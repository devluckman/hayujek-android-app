package com.man.hayujek.domain.event

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
interface StateEventSubscriber<T> {
    fun onIdle()
    fun onLoading()
    fun onFailure(throwable: Throwable)
    fun onSuccess(data: T)
}