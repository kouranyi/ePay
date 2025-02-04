package com.efinance.epay.data.repository

import androidx.lifecycle.LiveData
import com.efinance.epay.data.local.TransDataDao
import com.efinance.epay.data.local.TransDataEntity
import com.efinance.epay.data.remote.ApiService
import com.efinance.epay.data.remote.PaymentRequest
import com.efinance.epay.data.remote.PaymentResponse
import com.efinance.epay.data.remote.TransactionData
import retrofit2.Response
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val api: ApiService,
    private val dao: TransDataDao
) {
    suspend fun processPayment(name: String, transData: TransactionData): Response<PaymentResponse> =
        api.processPayment(
            PaymentRequest(name = name, transData = transData)
        )

    suspend fun saveTransaction(transaction: TransDataEntity) = dao.insertTransaction(transaction)

    fun getAllTransactions(): LiveData<List<TransDataEntity>> = dao.getAllTransactions()

    suspend fun deleteTransaction(transaction: TransDataEntity) = dao.deleteTransaction(transaction)
}