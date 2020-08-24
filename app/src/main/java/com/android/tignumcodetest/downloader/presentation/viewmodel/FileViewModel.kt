package com.android.tignumcodetest.downloader.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.tignumcodetest.downloader.domain.model.Files
import com.android.tignumcodetest.downloader.data.repository.FileDbRepository


class FileViewModel : ViewModel()
    {

    //Database Operations in view model

    // Method #1
    fun insert(files: Files) {
        return FileDbRepository.getInstance().insert(files)
    }

    // Method #2
    fun delete(note: Files) {
        FileDbRepository.getInstance().delete(note)
    }


    // Method #3
    fun getAllFiles(): LiveData<List<Files>>? {
        return FileDbRepository.getInstance().getAllfiles()
    }

        // Method #4
    fun isDataExistsinRoomDb(url: String?): Int{
        return FileDbRepository.getInstance().isDataExists(url)
    }


}