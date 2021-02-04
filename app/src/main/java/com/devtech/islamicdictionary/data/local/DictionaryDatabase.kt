package com.devtech.islamicdictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devtech.islamicdictionary.data.local.entity.Dictionary

@Database(entities = [Dictionary::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao

}