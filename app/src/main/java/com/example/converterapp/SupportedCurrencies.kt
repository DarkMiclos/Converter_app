package com.example.converterapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.databinding.FragmentSupportedCurrenciesBinding
import org.json.JSONObject
import java.net.URL
import kotlin.math.log


class SupportedCurrencies : Fragment() {
    private lateinit var currencyList: ArrayList<Currency>
    private lateinit var currencyAdapter: CurrencyAdapter
    private var _binding: FragmentSupportedCurrenciesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSupportedCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context);

        currencyList = ArrayList()

        currencyList.add(Currency("HUF"))
        currencyList.add(Currency("USD"))
        currencyList.add(Currency("EUR"))
        currencyList.add(Currency("GBP"))
        currencyList.add(Currency("SEK"))
        currencyList.add(Currency("DKK"))
        currencyList.add(Currency("AUD"))
        currencyList.add(Currency("JPY"))
        currencyList.add(Currency("CAD"))

        currencyAdapter = CurrencyAdapter(currencyList)
        binding.recyclerView.adapter = currencyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}