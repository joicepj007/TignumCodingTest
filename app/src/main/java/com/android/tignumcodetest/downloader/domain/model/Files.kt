package com.android.tignumcodetest.downloader.domain.model
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "tbl_file")
    class Files(@PrimaryKey(autoGenerate = true)
               var id: Int,
                var url: String?,
                var dirPath:String?,
                var dirName: String?) :Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())


    // Method #1
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    // Method #2
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(url)
        parcel.writeString(dirPath)
        parcel.writeString(dirName)
    }

    // Method #3
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Files> {
        // Method #4
        override fun createFromParcel(parcel: Parcel): Files {
            return Files(
                parcel
            )
        }

        // Method #5
        override fun newArray(size: Int): Array<Files?> {
            return arrayOfNulls(size)
        }
    }

    // Method #6
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (dirPath?.hashCode() ?: 0)
        result = 31 * result + (dirName?.hashCode() ?: 0)
        return result
    }


}
