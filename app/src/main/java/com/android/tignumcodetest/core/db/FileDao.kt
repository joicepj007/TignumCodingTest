package com.android.tignumcodetest.core.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.tignumcodetest.downloader.domain.model.Files

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFile(files: Files): Long

    @Delete
    fun deleteFile(files: Files)

    @Query("SELECT * from tbl_file")
    fun getAllFiles() : LiveData<List<Files>>

    @Query("SELECT * FROM tbl_file WHERE url = :url")
    fun isDataExist(url: String?): Int

}