package com.example.converterapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.converterapp.databinding.FragmentCurrencyConverterBinding
import com.example.converterapp.databinding.FragmentSupportedCurrenciesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrencyConverter.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrencyConverter : Fragment() {
    private var _binding: FragmentCurrencyConverterBinding? = null
    private val binding get() = _binding!!
    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCurrencyConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerSetup()
        textChanged()
    }

    private fun getApiResult() {
        if(binding.etFirstConversion != null && binding.etFirstConversion.text.isNotEmpty() && binding.etFirstConversion.text.isNotBlank()) {
            val API = "https://open.er-api.com/v6/latest/$baseCurrency"

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiResult = URL(API).readText()
                    val jsonObject = JSONObject(apiResult)

                    conversionRate = jsonObject.getJSONObject("rates").getString(convertedToCurrency).toFloat()

                    withContext(Dispatchers.Main) {
                        val text = (binding.etFirstConversion.text.toString().toFloat() * conversionRate).toString()
                        binding.etSecondConversion?.setText(text)
                    }

                    Log.d("Conversion", "$conversionRate")
                    Log.d("Conversion", apiResult)
                } catch (e: Exception) {
                    Log.d("Conversion", "$e")
                }
            }
        }
    }

    private fun spinnerSetup() {
        val spinner: Spinner = binding.spinnerFirstConversion

        ArrayAdapter.createFromResource(binding.root.context, R.array.currencies, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerFirstConversion.adapter = adapter
            }

        ArrayAdapter.createFromResource(binding.root.context, R.array.currencies2, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerSecondConversion.adapter = adapter
            }
        binding.spinnerFirstConversion.onItemSelectedListener = object: OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.spinnerSecondConversion.onItemSelectedListener = object: OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun textChanged() {
        binding.etFirstConversion.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Conversion", "Before text changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Conversion", "On text changed")
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    getApiResult()
                } catch (e: Exception) {
                    Log.d("Conversion", "$e")
                }
            }

        })
    }
}