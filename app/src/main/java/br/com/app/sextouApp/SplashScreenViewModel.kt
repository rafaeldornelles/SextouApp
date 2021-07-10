package br.com.app.sextouApp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SplashScreenViewModel (application: Application) : AndroidViewModel(application) {

    private val mLoggedUser = MutableLiveData<Boolean>()
    var loggedUser : LiveData<Boolean> = mLoggedUser



    fun verifyLoggedUser() {
        //"verifica se o usuario esta logado"
        mLoggedUser.value = true
    }
}