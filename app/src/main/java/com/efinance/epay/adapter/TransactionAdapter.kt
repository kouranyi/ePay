package com.efinance.epay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efinance.epay.R
import com.efinance.epay.data.local.TransDataEntity


class TransactionAdapter(
    private val onDeleteClick: (TransDataEntity) -> Unit
) : ListAdapter<TransDataEntity, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction, onDeleteClick)
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView: TextView = itemView.findViewById(R.id.transactionAmount)
        private val dateTextView: TextView = itemView.findViewById(R.id.transactionDate)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.deleteTransaction)

        fun bind(transaction: TransDataEntity, onDeleteClick: (TransDataEntity) -> Unit) {
            amountTextView.text = "${transaction.amount}"
            dateTextView.text = transaction.timeStamp
            deleteIcon.setOnClickListener { onDeleteClick(transaction) }
        }
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<TransDataEntity>() {
    override fun areItemsTheSame(oldItem: TransDataEntity, newItem: TransDataEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TransDataEntity, newItem: TransDataEntity): Boolean {
        return oldItem == newItem
    }
}
