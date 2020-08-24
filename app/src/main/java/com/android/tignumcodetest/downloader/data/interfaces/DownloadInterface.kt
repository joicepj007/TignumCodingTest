package com.android.tignumcodetest.downloader.data.interfaces

import com.downloader.Error
import com.downloader.Progress

interface DownloadInterface {

    interface downloadViewPresenter {
        fun setOnStartOrResumeListener(downloadId: Int)
        fun setOnPauseListener()
        fun setOnCancelListener()
        fun setOnProgressListener(progress: Progress)
        fun OnDownloadListener()
        fun onDownloadError(error: Error?)
    }


}