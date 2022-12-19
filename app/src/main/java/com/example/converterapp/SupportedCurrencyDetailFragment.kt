package com.example.converterapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.converterapp.databinding.FragmentSupportedCurrenciesBinding
import com.example.converterapp.databinding.FragmentSupportedCurrencyDetailBinding


class SupportedCurrencyDetailFragment : Fragment() {
    private var _binding: FragmentSupportedCurrencyDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSupportedCurrencyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SupportedCurrencyDetailFragmentArgs by navArgs()
        binding.currencyName.text = args.currencyName
    }
}