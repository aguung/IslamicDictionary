package com.devtech.islamicdictionary.data.network

import com.devtech.islamicdictionary.data.network.response.DictionaryResponse
import com.devtech.islamicdictionary.data.network.response.NetworkResult
import okhttp3.ResponseBody
import retrofit2.http.GET

interface DictionaryApi {
//    @GET("exec?action=get")
//    suspend fun getDictionary(): NetworkResult<DictionaryResponse>

    @GET("exec?action=read")
    suspend fun getDictionary(): DictionaryResponse
}