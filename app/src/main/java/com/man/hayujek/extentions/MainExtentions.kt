package com.man.hayujek.extentions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 *
 * Created by Lukmanul Hakim on  31/07/22
 * devs.lukman@gmail.com
 */

fun Activity.isPermissionGranted(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Fragment.isPermissionGranted(permission: String) =
    ActivityCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
