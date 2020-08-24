package com.android.tignumcodetest

import android.app.Application
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import kotlin.properties.Delegates

class App : Application() {

    companion object {
        @JvmStatic
        private val TAG = App::class.java.simpleName
        var instance: App by Delegates.notNull()
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        val config: PRDownloaderConfig = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .setReadTimeout(30_000)
            .setConnectTimeout(30_000)
            .build()
        PRDownloader.initialize(this, config)
    }

}