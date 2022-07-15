package com.man.hayujek.data.network

import android.content.Context
import com.man.hayujek.data.network.utils.OkHttpClientFactory
import okhttp3.OkHttpClient
import org.koin.core.annotation.Single

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
@Single
class ApiServicesProvider(val context: Context) {

    private fun client() : OkHttpClient = OkHttpClientFactory.create(context = context)

    fun get() : ApiServices = ApiServices.build(client())

}