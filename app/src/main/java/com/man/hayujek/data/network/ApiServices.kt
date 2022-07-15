package com.man.hayujek.data.network

import com.man.hayujek.BuildConfig
import com.man.hayujek.domain.request.RequestLogin
import com.man.hayujek.data.response.LoginResponse
import com.man.hayujek.data.response.ProfileResponse
import com.man.hayujek.data.response.RegisterResponse
import com.man.hayujek.domain.request.RequestRegister
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */

interface ApiServices {

    // region API Customer

    @POST(EndPoint.LOGIN_CUSTOMER)
    suspend fun loginCustomer(
        @Body request: RequestLogin
    ): Response<LoginResponse>

    @POST(EndPoint.REGISTER_CUSTOMER)
    suspend fun registerCustomer(
        @Body request: RequestRegister
    ): Response<RegisterResponse>

    @GET(EndPoint.GET_CUSTOMER)
    suspend fun getProfileCustomer(
        @Header("Authorization") token : String
    ): Response<ProfileResponse>

    // endregion

    // region API Driver

    @POST(EndPoint.LOGIN_DRIVER)
    suspend fun loginDriver(
        @Body request: RequestLogin
    ): Response<LoginResponse>

    @POST(EndPoint.REGISTER_DRIVER)
    suspend fun registerDriver(
        @Body request: RequestRegister
    ): Response<RegisterResponse>

    @GET(EndPoint.GET_DRIVER)
    suspend fun getProfileDriver(
        @Header("Authorization") token : String
    ): Response<ProfileResponse>

    // endregion

    // region Object

    companion object {
        fun build(client: OkHttpClient): ApiServices {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiServices::class.java)
        }
    }

    object EndPoint {
        const val LOGIN_CUSTOMER = "/v1/api/customer/login"
        const val REGISTER_CUSTOMER = "/v1/api/customer/register"
        const val GET_CUSTOMER = "/v1/api/customer"
        const val LOGIN_DRIVER = "/v1/api/driver/login"
        const val REGISTER_DRIVER = "/v1/api/driver/register"
        const val GET_DRIVER = "/v1/api/driver"
    }

    // endregion
}