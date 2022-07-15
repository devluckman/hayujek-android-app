package com.man.hayujek.utils

import android.util.Log
import com.man.hayujek.BuildConfig

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */
object Constants {

    const val TOKEN_KEY = "TOKEN_KEY"

}

fun log(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d("WKWKWK", message)
    }
}