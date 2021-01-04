package com.devtech.islamicdictionary.di

import android.app.Application
import androidx.room.Room
import com.devtech.islamicdictionary.BuildConfig
import com.devtech.islamicdictionary.data.local.DictionaryDatabase
import com.devtech.islamicdictionary.data.network.DictionaryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDictionaryApi(retrofit: Retrofit): DictionaryApi =
        retrofit.create(DictionaryApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, DictionaryDatabase::class.java, "dictionary_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDictionaryDao(db: DictionaryDatabase) = db.dictionaryDao()

}

