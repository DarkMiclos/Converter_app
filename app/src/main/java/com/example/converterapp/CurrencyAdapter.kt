package com.example.converterapp

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CurrencyAdapter(private var currencyList: ArrayList<Currency>,
    private val listener: OnItemClickListener): RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var onItemClick: ((Currency) -> Unit)? = null

    inner class CurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    OnClickListener{
        val textView: TextView = itemView.findViewById(R.id.textView)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.textView.text = currency.name
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun searchCurrency(newCurrencyList: ArrayList<Currency>) {
        val diffUtil = SupportedCurrenciesDiffUtil(currencyList, newCurrencyList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        currencyList = newCurrencyList
        diffResults.dispatchUpdatesTo(this)
    }
}