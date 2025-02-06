package com.efinance.epay.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efinance.epay.data.local.TransDataEntity
import com.efinance.epay.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactions = MutableLiveData<List<TransDataEntity>>()
    val transactions: LiveData<List<TransDataEntity>> = _transactions

    fun fetchTransactions() {
        viewModelScope.launch {
            val transactions = repository.getAllTransactions()
            _transactions.value = transactions
        }
    }


    fun deleteTransaction(transaction: TransDataEntity) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
            fetchTransactions()
        }
    }
}
