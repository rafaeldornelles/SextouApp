package br.com.app.sextouApp.ui.signUp

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.app.sextouApp.utils.Validator

class SignUpViewModel: ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val profilePic = MutableLiveData<Bitmap>()

    fun formIsValid(): Boolean {
        return Validator.EMAIL.validate(email.value) == null &&
                Validator.PASSWORD.validate(password.value) == null &&
                Validator.NAME.validate(name.value) == null
    }
}