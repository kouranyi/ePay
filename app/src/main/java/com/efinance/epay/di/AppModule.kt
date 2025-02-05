package com.efinance.epay.di

import android.content.Context
import androidx.room.Room
import com.efinance.epay.data.local.TransDataDao
import com.efinance.epay.data.local.TransactionDatabase
import com.efinance.epay.data.remote.ApiService
import com.efinance.epay.data.remote.RetrofitInstance
import com.efinance.epay.utils.Constants.EPAY_DATABAE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService(): ApiService = RetrofitInstance.api

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TransactionDatabase =
        Room.databaseBuilder(context, TransactionDatabase::class.java, EPAY_DATABAE_NAME).build()

    @Provides
    fun provideDao(db: TransactionDatabase): TransDataDao = db.transactionDao()
}