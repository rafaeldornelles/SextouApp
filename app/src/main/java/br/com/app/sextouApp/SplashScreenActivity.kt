package br.com.app.sextouApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        mViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        mViewModel.verifyLoggedUser()
        observer()
    }

    private fun observer() {
        mViewModel.loggedUser.observe(this, Observer {

            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                  login(it)
                }
            },1500)

        })

    }

    private fun login(it: Boolean?) {
        if(it==true){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        finish()
    }
}