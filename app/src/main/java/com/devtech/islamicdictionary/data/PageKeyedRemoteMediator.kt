package com.devtech.islamicdictionary.data

import androidx.paging.*
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.room.withTransaction
import com.devtech.islamicdictionary.data.local.DictionaryDatabase
import com.devtech.islamicdictionary.data.local.entity.Dictionary
import com.devtech.islamicdictionary.data.network.DictionaryApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PageKeyedRemoteMediator(
    private val dictionaryApi: DictionaryApi,
    private val database: DictionaryDatabase
) : RemoteMediator<Int, Dictionary>() {
    val dictionary = database.dictionaryDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Dictionary>): MediatorResult {
        return try {
            when (loadType) {
                PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.nomor
                }
                REFRESH -> println("Refresh")
            }
            val response = dictionaryApi.getDictionary()
            database.withTransaction {
//                dictionary.deleteAll()
                dictionary.insertAll(response.records)
            }

            MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}