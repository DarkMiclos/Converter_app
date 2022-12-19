package com.example.converterapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.converterapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)

        binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true

            when(it.itemId) {
                R.id.nav_currency_converter -> {
                    navController.navigate(R.id.currencyConverter)
                    binding.drawerLayout.closeDrawers()
                }
                R.id.nav_length_converter -> {
                    navController.navigate(R.id.lengthConverter)
                    binding.drawerLayout.closeDrawers()
                }
                R.id.nav_supported_currencies -> {
                    navController.navigate(R.id.supportedCurrencies)
                    binding.drawerLayout.closeDrawers()
                }
                R.id.nav_logout -> {
                    navController.navigate(R.id.loginRegister)
                    binding.drawerLayout.closeDrawers()
                }
            }
            true
        }

        viewModel.userName.observe(this, Observer {
            if(it.isNotEmpty()) {
                var header: View = binding.navigationView.getHeaderView(0)
                var text: TextView = header.findViewById(R.id.headerUserName)
                text.text = viewModel.userName.value.toString()
            }
        })
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerView.id, fragment)
        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        /*if(viewModel.isLoggedIn.value == true && binding.navigationView.menu.findItem(100) == null) {
            binding.navigationView.menu.removeItem(R.id.nav_login_register)
            var item: MenuItem = binding.navigationView.menu.add(Menu.NONE, 100, 5, "Logout")
        }*/

        return true
    }

}