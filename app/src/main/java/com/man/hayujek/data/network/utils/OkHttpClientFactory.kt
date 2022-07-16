package com.man.hayujek.data.network.utils

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.man.hayujek.BuildConfig
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
object OkHttpClientFactory {

    private const val DEFAULT_MAX_REQUEST = 30
    private const val TIMEOUT = 40

    fun create(context: Context): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor).build()

            builder.addInterceptor(createChuck(context))
        }

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = DEFAULT_MAX_REQUEST
        builder.dispatcher(dispatcher)

        return builder.build()
    }

    private fun createChuck(context: Context): ChuckerInterceptor {
        val checkerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context)
            .collector(checkerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }
}