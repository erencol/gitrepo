package com.erencol.igitrepo.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GitRepo(
    var id: Long = 0,
    @SerializedName("name")
    var repoName: String? = "",
    var userName: String? = "",
    @SerializedName("owner")
    var owner: Owner?,
    @SerializedName("stargazers_count")
    var repoStarCount: Int = 0,
    @SerializedName("open_issues_count")
    var openIssueCount: Int = 0,
    var isStarred: Boolean = false) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Owner::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(repoName)
        parcel.writeString(userName)
        parcel.writeParcelable(owner, flags)
        parcel.writeInt(repoStarCount)
        parcel.writeInt(openIssueCount)
        parcel.writeByte(if (isStarred) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GitRepo> {
        override fun createFromParcel(parcel: Parcel): GitRepo {
            return GitRepo(parcel)
        }

        override fun newArray(size: Int): Array<GitRepo?> {
            return arrayOfNulls(size)
        }
    }
}