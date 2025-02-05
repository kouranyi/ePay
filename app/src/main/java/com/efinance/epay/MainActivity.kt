package com.efinance.epay

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.efinance.epay.data.remote.TransactionData
import com.efinance.epay.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val amount = 500.9
        if (amount != null) {
            AlertDialog.Builder(this)
                .setTitle("Confirm Payment")
                .setMessage("Are you sure you want to pay $$amount?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.processPayment("Sale", TransactionData(amount, 1, 1))
                }
                .setNegativeButton("No", null)
                .show()
        }
        viewModel.paymentResult.observe(this) { resource ->
            when (resource) {
                is State.Loading -> {
                    findViewById<View>(R.id.loading_state).visibility = View.VISIBLE
                }
                is State.Success -> {
                    findViewById<View>(R.id.loading_state).visibility = View.GONE
                    Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
                }
                is State.Error -> {
                    findViewById<View>(R.id.loading_state).visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}