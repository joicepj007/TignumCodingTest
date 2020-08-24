package com.android.tignumcodetest.core.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater


class AppUtils {

    companion object {

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (cm != null)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val network: Network = cm.activeNetwork ?: return false
                    val capabilities: NetworkCapabilities? =
                        cm.getNetworkCapabilities(network) ?: return false
                    return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ))
                } else {
                    val activeNetwork = cm.activeNetworkInfo
                    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
                }
            return false
        }

         fun getFileTypeFromURL(url: String): String? {
            val splitedArray =
                url.split("\\.".toRegex()).toTypedArray()
            val lastValueOfArray = splitedArray[splitedArray.size - 1]
            return if (lastValueOfArray == "mp4" || lastValueOfArray == "flv" || lastValueOfArray == "m4a" || lastValueOfArray == "3gp" || lastValueOfArray == "mkv") {
                "video"
            } else if (lastValueOfArray == "mp3" || lastValueOfArray == "ogg") {
                "audio"
            } else if (lastValueOfArray == "jpg" || lastValueOfArray == "png" || lastValueOfArray == "gif") {
                "photo"
            } else {
                ""
            }
        }


    }

}