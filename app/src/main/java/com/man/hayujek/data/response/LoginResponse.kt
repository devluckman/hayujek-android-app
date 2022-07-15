package com.man.hayujek.data.response

import com.google.gson.annotations.SerializedName
import com.man.hayujek.base.BaseResponse

class LoginResponse : BaseResponse<LoginResponse.Data>() {
    class Data(
        @SerializedName("role")
        val role: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("token")
        val token: String? = null
    )


}


