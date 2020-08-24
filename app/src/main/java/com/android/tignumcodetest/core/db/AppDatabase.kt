package com.android.tignumcodetest.core.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.tignumcodetest.App
import com.android.tignumcodetest.downloader.domain.model.Files


@Database(entities = [Files::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fileDao(): FileDao
    companion object {
        const val DB_NAME = "file.db"
        const val VERSION = 1
        private val instance: AppDatabase by lazy {
            create(
                App.instance
            )
        }

        @Synchronized
        internal fun getInstance(): AppDatabase {
            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java,
                DB_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

            }
    }

