package com.devtech.islamicdictionary.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var nama: String,
    var gambar: Int,
    var background: Int,
    var posisi: Int,
    var navigate: Int
) : Parcelable