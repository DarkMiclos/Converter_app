package com.example.converterapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.ListFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.databinding.FragmentSupportedCurrenciesBinding
import org.json.JSONObject
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class SupportedCurrencies : Fragment(), CurrencyAdapter.OnItemClickListener {
    private lateinit var currencyList: ArrayList<Currency>
    private lateinit var tempCurrencyList: ArrayList<Currency>
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
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        currencyList = ArrayList()
        tempCurrencyList = ArrayList()

        currencyList.add(Currency("HUF"))
        currencyList.add(Currency("USD"))
        currencyList.add(Currency("EUR"))
        currencyList.add(Currency("GBP"))
        currencyList.add(Currency("SEK"))
        currencyList.add(Currency("DKK"))
        currencyList.add(Currency("AUD"))
        currencyList.add(Currency("JPY"))
        currencyList.add(Currency("CAD"))

        tempCurrencyList.addAll(currencyList)

        currencyAdapter = CurrencyAdapter(currencyList, this)
        val headerAdapter: HeaderAdapter = HeaderAdapter()
        binding.recyclerView.adapter = ConcatAdapter(headerAdapter, currencyAdapter)
        binding.searchAction.clearFocus()
        binding.searchAction.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val clickedItem = currencyList[position]
        Log.d("clicked", clickedItem.toString())
        var action = SupportedCurrenciesDirections.actionSupportedCurrenciesToSupportedCurrenciesDetails2(clickedItem.name)
        findNavController().navigate(action)
    }

    private fun filterList(text: String?) {
        if(text != null) {
            val filteredList: ArrayList<Currency> = ArrayList<Currency>()
            for (i in currencyList) {
                if (i.name.lowercase(Locale.getDefault()).contains(text.lowercase())) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(context, "There is no currency with this name", Toast.LENGTH_SHORT).show()
            } else {
                currencyAdapter.searchCurrency(filteredList)
            }
        }
    }
}