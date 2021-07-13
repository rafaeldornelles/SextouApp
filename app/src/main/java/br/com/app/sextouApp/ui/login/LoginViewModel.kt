package br.com.app.sextouApp.ui.login

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.app.sextouApp.utils.Validator
import br.com.app.sextouApp.utils.Validators

class LoginViewModel: ViewModel() {
    val login = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun formIsValid(): Boolean{
        return Validator.EMAIL.validate(login.value) == null && password.value?.isNotBlank()?: false
    }
}