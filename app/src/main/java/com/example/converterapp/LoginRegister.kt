package com.example.converterapp

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.converterapp.databinding.FragmentLoginRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private lateinit var viewModel: UserViewModel
    private lateinit var myMenu: Menu

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

        val navController = findNavController()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        appDatabase = AppDatabase.getDatabase(binding.root.context)

        binding.signup.setOnClickListener {
            writeData()
        }

        binding.login.setOnClickListener {
            readData()
            navController.navigate(R.id.action_loginRegister_to_currencyConverter2)
        }
    }

    private fun writeData() {
        var firstName = binding.firstName.text.toString()
        var secondName = binding.secondName.text.toString()
        var password = binding.password.text.toString()

        if(firstName.isNotEmpty() && secondName.isNotEmpty() && password.isNotEmpty()) {
            val user = User(
                null, firstName, secondName, password)
            GlobalScope.launch(Dispatchers.IO) {
                appDatabase.userDao().insert(user)
            }

            binding.firstName.text.clear()
            binding.secondName.text.clear()
            binding.password.text.clear()

            Toast.makeText(context, "Successful sign up!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please enter data!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData() {
        val firstName = binding.firstName.text.toString()
        val secondName = binding.secondName.text.toString()
        val password = binding.password.text.toString()

        if(firstName.isNotEmpty() && secondName.isNotEmpty() && password.isNotEmpty()) {
            lateinit var user: User
            GlobalScope.launch {
                user = appDatabase.userDao().findByFirstAndSecondName(firstName, secondName)

                if(user.password == password) {
                    withContext(Dispatchers.Main) {
                        viewModel.setLoggedIn(true)
                        viewModel.setUser(user)
                        Log.d("login", viewModel.isLoggedIn.toString())
                    }
                    Log.d("Login", "Successful login!")
                } else {
                    Log.d("Login", "Password is incorrect!")
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = fragment.parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
        activity?.title = title
    }
}