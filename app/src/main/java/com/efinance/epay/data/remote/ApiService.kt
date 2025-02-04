package com.efinance.epay.data.remote

import com.efinance.epay.utils.Constants.PAYMENTS_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

 interface ApiService {
    @POST(PAYMENTS_ENDPOINT)
    suspend fun processPayment(@Body paymentRequest: PaymentRequest): Response<PaymentResponse>
}