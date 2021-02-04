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
    fun getSearchDictionary(type: String, search: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(dictionaryApi, db)
    ) {
        if(type != "0"){
            if (search.isNotEmpty()) {
                db.dictionaryDao().getSearchByType(type,"%$search%")
            } else {
                db.dictionaryDao().getAllPagedByType(type)
            }
        }else{
            if (search.isNotEmpty()) {
                db.dictionaryDao().getSearch("%$search%")
            } else {
                db.dictionaryDao().getAllPaged()
            }
        }
    }.flow

}

