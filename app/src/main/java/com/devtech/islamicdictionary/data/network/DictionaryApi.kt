package com.devtech.islamicdictionary.data.network

import com.devtech.islamicdictionary.data.network.response.DictionaryResponse
import retrofit2.http.GET

interface DictionaryApi {
    @GET("exec?action=read")
    suspend fun getDictionary(): DictionaryResponse
}