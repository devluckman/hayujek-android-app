package com.man.hayujek.domain.request

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
data class RequestRegister (
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)