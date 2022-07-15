package com.man.hayujek.base

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by Lukmanul Hakim on  14/07/22
 * devs.lukman@gmail.com
 */
open class BaseResponse<T> {

    @SerializedName("data")
    val data: T? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("status")
    val status: Boolean? = null

}