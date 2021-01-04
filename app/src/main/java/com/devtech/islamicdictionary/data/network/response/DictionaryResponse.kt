package com.devtech.islamicdictionary.data.network.response


import com.devtech.islamicdictionary.data.local.entity.Dictionary
import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @SerializedName("records")
    val records: List<Dictionary> = arrayListOf()
)