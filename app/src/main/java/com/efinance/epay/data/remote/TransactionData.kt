package com.efinance.epay.data.remote

data class TransactionData(
    val amount:Double,
    val batchNumber:Int,
    val traceNumber:Int
)
