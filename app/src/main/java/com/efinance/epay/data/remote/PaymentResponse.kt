package com.efinance.epay.data.remote

import com.google.gson.annotations.SerializedName

 data class PaymentResponse(
   @SerializedName("id") val id: String,
   @SerializedName("name") val name: String,
   @SerializedName("createdAt") val createdAt: String,
   @SerializedName("data") val data: TransactionData
)