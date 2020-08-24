package com.android.tignumcodetest.downloader.domain.presenter

import com.android.tignumcodetest.downloader.data.interfaces.DownloadInterface
import com.android.tignumcodetest.downloader.presentation.model.DownloadModelImpl
import com.downloader.Error
import com.downloader.Progress

class DownloadPresenterImpl(private val downloadDataView: DownloadInterface.downloadViewPresenter?,
                            private val downloadModelImpl: DownloadModelImpl
)
{

    fun fileDownload(url: String,path: String,filename: String) {

        downloadModelImpl.getDownLoadFile(url,path,filename,object : DownloadModelImpl.DownloadResponseCallback{
            override fun setOnStartOrResumeListener(downloadId: Int) {

                downloadDataView?.setOnStartOrResumeListener(downloadId)
            }

            override fun setOnPauseListener() {

                downloadDataView?.setOnPauseListener()
            }

            override fun setOnCancelListener() {
                 downloadDataView?.setOnCancelListener()
            }

            override fun setOnProgressListener(progress: Progress) {
                 downloadDataView?.setOnProgressListener(progress)
            }

            override fun OnDownloadListener() {
                 downloadDataView?.OnDownloadListener()
            }

            override fun onDownloadError(error: Error?) {
                 downloadDataView?.onDownloadError(error)
            }
        })
    }



}