package com.man.hayujek.data.response

import com.google.gson.annotations.SerializedName
import com.man.hayujek.base.BaseResponse

class ProfileResponse : BaseResponse<ProfileResponse.Data>() {
    class Data(
        @SerializedName("role")
        val role: String? = null,

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("username")
        val username: String? = null
    )
}

