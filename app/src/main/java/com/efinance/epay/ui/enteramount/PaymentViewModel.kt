package com.efinance.epay.ui.enteramount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efinance.epay.data.local.TransDataEntity
import com.efinance.epay.data.remote.PaymentResponse
import com.efinance.epay.data.remote.TransactionData
import com.efinance.epay.data.repository.TransactionRepository
import com.efinance.epay.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {
    private val _paymentResult = MutableLiveData<State<PaymentResponse>?>()
    val paymentResult: MutableLiveData<State<PaymentResponse>?> get() = _paymentResult

    fun processPayment(name: String, transData: TransactionData) = viewModelScope.launch {
        _paymentResult.postValue(State.Loading())
        try {
            val response = repository.processPayment(name,transData)
            if (response.isSuccessful) {
                response.body()?.let {
                    repository.saveTransaction(TransDataEntity(amount = response.body()!!.data.amount, timeStamp = response.body()!!.createdAt ))
                    _paymentResult.postValue(State.Success(it))
                }
            } else {
                _paymentResult.postValue(State.Error("Payment failed"))
            }
        } catch (e: Exception) {
            _paymentResult.postValue(State.Error(e.message ?: "Error"))
        }
    }
    fun onPaymentResultHandled() {
        _paymentResult.value = null
    }
}
