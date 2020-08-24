package com.android.tignumcodetest.downloader.presentation.model

import com.downloader.*

class DownloadModelImpl
{
    private var downloadId: Int = 0
     fun getDownLoadFile(
        url: String,
        path: String,
        filename: String,
        callback: DownloadResponseCallback?
    ) {
        downloadId= PRDownloader.download(url,path,filename)
            .build()
            .setOnStartOrResumeListener(object : OnStartOrResumeListener {
                override fun onStartOrResume() {

                    callback?.setOnStartOrResumeListener(downloadId)
                }
            })
            .setOnPauseListener(object : OnPauseListener {
                override fun onPause() {
                    callback?.setOnPauseListener()
                }
            })
            .setOnCancelListener(object : OnCancelListener {
                override fun onCancel() {
                    downloadId = 0;
                    callback?.setOnCancelListener()
                }
            })
            .setOnProgressListener(object : OnProgressListener {
                override fun onProgress(progress: Progress) {
                    callback?.setOnProgressListener(progress)

                }
            })
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {

                    callback?.OnDownloadListener()

                }

                override fun onError(error: Error?) {
                    downloadId = 0;
                    callback?.onDownloadError(error)

                }

            })

    }


    interface DownloadResponseCallback {
        fun setOnStartOrResumeListener(downloadId: Int)
        fun setOnPauseListener()
        fun setOnCancelListener()
        fun setOnProgressListener(progress: Progress)
        fun OnDownloadListener()
        fun onDownloadError(error: Error?)
    }


}