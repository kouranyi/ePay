package com.efinance.epay.data.remote

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("data")
    val transData: TransactionData
)
