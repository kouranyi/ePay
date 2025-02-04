package com.efinance.epay.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transData")
data class TransDataEntity(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val amount:Double,
    val timeStamp:Long
)