package br.com.app.sextouApp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenViewModel (application: Application) : AndroidViewModel(application) {

    private val mLoggedUser = MutableLiveData<Boolean>()
    var loggedUser : LiveData<Boolean> = mLoggedUser



    fun verifyLoggedUser() {
        val user = Firebase.auth.currentUser
        mLoggedUser.value = user!=null
    }
}