package com.efinance.epay.ui.enteramount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.efinance.epay.R
import com.efinance.epay.data.remote.TransactionData
import com.efinance.epay.databinding.FragmentEnterAmountBinding
import com.efinance.epay.utils.State
import com.efinance.epay.utils.checkForInternet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterAmountFragment : Fragment() {
    private var _binding: FragmentEnterAmountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PaymentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterAmountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observePaymentResult()

    }

    private fun observePaymentResult() {
        viewModel.paymentResult.observe(viewLifecycleOwner) { response ->
            response?.let {
                when (response) {
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is State.Success -> {
                        binding.progressBar.visibility = View.GONE
                        resetAmount()
                        Toast.makeText(requireContext(), "Payment Successful!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is State.Error -> {
                        binding.progressBar.visibility = View.GONE
                        resetAmount()
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.onPaymentResultHandled()
            }

        }
    }

    private fun resetAmount() {
        binding.amountEditText.setHint(resources.getString(R.string.enter_amount))
        binding.amountEditText.setText("")
    }

    private fun setListeners() {
        binding.confirmPaymentButton.setOnClickListener {
            val amount = binding.amountEditText.text.toString().trim()
            if (amount.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid amount", Toast.LENGTH_SHORT).show()
            } else {
                promptConfirmAmount(amount)
            }
        }
    }

    private fun promptConfirmAmount(amount: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Payment")
            .setMessage("Are you sure you want to pay $amount EGP?")
            .setPositiveButton("Yes") { _, _ ->
                startPaymentProcess(amount)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun startPaymentProcess(amount: String) {
        if(checkForInternet(requireContext())){
            viewModel.processPayment("Sale Transaction", TransactionData(amount.toDouble(), 1, 1))
        }else{
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}