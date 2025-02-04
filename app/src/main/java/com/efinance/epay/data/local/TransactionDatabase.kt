package com.efinance.epay.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransDataEntity::class], version = 1)
abstract class TransactionDatabase:RoomDatabase() {
    abstract fun transactionDao(): TransDataDao
}