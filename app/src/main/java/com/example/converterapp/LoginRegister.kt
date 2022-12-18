package com.example.converterapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.converterapp.databinding.FragmentLoginRegisterBinding
import com.example.converterapp.databinding.FragmentSupportedCurrenciesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginRegister.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginRegister : Fragment() {
    private var _binding: FragmentLoginRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getDatabase(binding.root.context)

        binding.signup.setOnClickListener {
            writeData()
        }

        binding.login.setOnClickListener {
            readData()
        }
    }

    private fun writeData() {
        var userName = binding.username.text.toString()
        var password = binding.password.text.toString()

        if(userName.isNotEmpty() && password.isNotEmpty()) {
            val user = User(
                null, userName, password)
            GlobalScope.launch(Dispatchers.IO) {
                appDatabase.userDao().insert(user)
            }

            binding.username.text.clear()
            binding.password.text.clear()

            Toast.makeText(context, "Successful sign up!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please enter data!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData() {
        val userName = binding.username.text.toString()
        val password = binding.password.text.toString()

        if(userName.isNotEmpty() && password.isNotEmpty()) {
            lateinit var user: User
            GlobalScope.launch {
                user = appDatabase.userDao().findByUserName(userName)

                if(user.password == password) {
                    Log.d("Login", "Successful login!")
                } else {
                    Log.d("Login", "Password is incorrect!")
                }
            }
        }
    }
}