package com.man.hayujek.extentions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

/**
 *
 * Created by Lukmanul Hakim on  13/07/22
 * devs.lukman@gmail.com
 */

fun navigateTo(fragment: Fragment, destination: Int) {
    try {
        NavHostFragment.findNavController(fragment).navigate(destination)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}
