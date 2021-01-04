package com.devtech.islamicdictionary.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "dictionary_table")
@Parcelize
data class Dictionary(
    @SerializedName("BahasaArab")
    val bahasaArab: String? = "",
    @SerializedName("NamaIstilah")
    val namaIstilah: String? = "",
    @SerializedName("PengertianIstilahEN")
    val pengertianIstilahEN: String? = "",
    @SerializedName("PengertianIstilahIND")
    val pengertianIstilahIND: String? = "",
    @SerializedName("Klasifikasi")
    val klasifikasi: String? = "6",
    @SerializedName("Bookmark")
    val bookmark: Boolean? = false,
    @SerializedName("Nomer")
    @PrimaryKey val nomor: Int? = 0
) : Parcelable