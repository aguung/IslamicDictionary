package com.devtech.islamicdictionary.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.devtech.islamicdictionary.data.local.entity.Dictionary

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary_table")
    fun getAll(): LiveData<List<Dictionary>>

    @Query("SELECT * FROM dictionary_table ORDER BY namaIstilah ASC")
    fun getAllPaged(): PagingSource<Int, Dictionary>

    @Query("SELECT * FROM dictionary_table WHERE namaIstilah LIKE :key OR pengertianIstilahIND LIKE :key ORDER BY nomor ASC")
    fun getSearch(key: String): PagingSource<Int, Dictionary>

    @Query("SELECT * FROM dictionary_table WHERE klasifikasi = :type ORDER BY namaIstilah ASC")
    fun getAllPagedByType(type: String): PagingSource<Int, Dictionary>

    @Query("SELECT * FROM dictionary_table WHERE klasifikasi = :type AND namaIstilah LIKE :key OR pengertianIstilahIND LIKE :key ORDER BY nomor ASC")
    fun getSearchByType(type: String,key: String): PagingSource<Int, Dictionary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dictionary: List<Dictionary>)

    @Query("DELETE FROM dictionary_table")
    fun deleteAll()

    @Delete
    fun delete(dictionary: Dictionary)
}