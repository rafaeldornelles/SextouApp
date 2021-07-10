package br.com.app.sextouApp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val login = MutableLiveData<String>()
    val password = MutableLiveData<String>()
}