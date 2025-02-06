package com.efinance.epay.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransDataDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransDataEntity)
    @Delete
    suspend fun deleteTransaction(transaction: TransDataEntity)
    @Query("SELECT * FROM transData ORDER BY timestamp DESC")
    suspend fun getAllTransactions(): List<TransDataEntity>
}
