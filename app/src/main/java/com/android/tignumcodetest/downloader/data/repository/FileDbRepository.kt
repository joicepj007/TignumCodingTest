package com.android.tignumcodetest.downloader.data.repository

import androidx.lifecycle.LiveData
import com.android.tignumcodetest.core.db.AppDatabase
import com.android.tignumcodetest.downloader.domain.model.Files
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FileDbRepository {

    // Method #1
    //function to insert file in database
    private val appDatabase: AppDatabase by lazy { AppDatabase.getInstance() }
    fun insert(files: Files) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.fileDao().insertFile(files)
        }
    }

    // Method #2
    //function to delete file in database
    fun delete(file: Files) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.fileDao().deleteFile(file)
        }
    }


    // Method #3
    //function to get all file in database
    fun  getAllfiles(): LiveData<List<Files>>{
        return appDatabase.fileDao().getAllFiles()
    }

    // Method #4
    //function to check data exists in database
    fun isDataExists(url: String?) : Int {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.fileDao().isDataExist(url)
        }
        return appDatabase.fileDao().isDataExist(url)
    }



    companion object {
        private var INSTANCE: FileDbRepository? = null
        fun getInstance() = INSTANCE
            ?: FileDbRepository()
                .also {
                INSTANCE = it
            }
    }
}