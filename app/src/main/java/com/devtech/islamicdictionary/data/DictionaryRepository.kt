package com.devtech.islamicdictionary.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.devtech.islamicdictionary.data.local.DictionaryDatabase
import com.devtech.islamicdictionary.data.network.DictionaryApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val db: DictionaryDatabase
) {

    @ExperimentalPagingApi
    fun getSearchDictionary(type: String, key: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(dictionaryApi, db)
    ) {
        if(type != "0"){
            if (key.isNotEmpty()) {
                db.dictionaryDao().getSearchByType(type,"%$key%")
            } else {
                db.dictionaryDao().getAllPagedByType(type)
            }
        }else{
            if (key.isNotEmpty()) {
                db.dictionaryDao().getSearch("%$key%")
            } else {
                db.dictionaryDao().getAllPaged()
            }
        }
    }.flow

}

