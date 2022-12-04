package com.example.converterapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyAdapter(private val currencyList: ArrayList<Currency>): RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var onItemClick: ((Currency) -> Unit)? = null

    class CurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.textView.text = currency.name
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currency)
        }
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }
}