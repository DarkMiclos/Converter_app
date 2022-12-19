package com.example.converterapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {

    private val _user =  MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    val userName: LiveData<String> = Transformations.map(user) {
        it.firstName + " " + it.secondName
    }

    fun setUser(user: User) {
        _user.value = user
    }

    private val _isLoggedIn = MutableLiveData<Boolean>()//Backing field
    val isLoggedIn: LiveData<Boolean>//Backing property
        get() =_isLoggedIn

    fun setLoggedIn(boolean: Boolean) {
        _isLoggedIn.value = boolean
    }
}