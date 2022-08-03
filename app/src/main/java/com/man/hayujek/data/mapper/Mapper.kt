package com.man.hayujek.data.mapper

import com.man.hayujek.data.response.LoginResponse
import com.man.hayujek.data.response.ProfileResponse
import com.man.hayujek.data.response.RegisterResponse
import com.man.hayujek.data.response.RoutesResponse
import com.man.hayujek.data.response.RoutesResponse.Companion.toDomain
import com.man.hayujek.domain.model.RouteModel
import com.man.hayujek.domain.model.UserModel

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
object Mapper {

    fun mapUserModel(response: ProfileResponse?) : UserModel {
        val mapper : (ProfileResponse.Data?) -> UserModel = {
            UserModel(name = it?.username.orEmpty())
        }

        return response?.data?.let(mapper) ?: UserModel(name = "Who Are you ?")
    }

    fun mapLogin(response: LoginResponse?) : String {
        val mapper : (LoginResponse.Data?) -> String = {
            it?.token ?: ""
        }
        return response?.data?.let(mapper) ?: ""
    }

    fun mapRegister(response: RegisterResponse?) : Boolean {
        val mapper : (Boolean?) -> Boolean = {
            it == true
        }
        return response?.data?.let(mapper) ?: false
    }

    fun mapRoute(response : RoutesResponse?) : RouteModel {
        val mapper : (RoutesResponse.Data?) -> RouteModel = {
            RouteModel(data = it.toDomain())
        }
        return response?.data?.let(mapper) ?: RouteModel(data = listOf())
    }

}