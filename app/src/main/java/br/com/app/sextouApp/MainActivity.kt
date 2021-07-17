package br.com.app.sextouApp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    val auth = Firebase.auth
    val storage = Firebase.storage

    val tabLayout by lazy {
        findViewById<TabLayout>(R.id.tab_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAuthStateListener()
        setSupportActionBar(findViewById(R.id.toolbar))
        hideTabLayout()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        auth.currentUser?.photoUrl?.let {
            val a = it.toString()
            storage.getReferenceFromUrl(it.toString())
                .getBytes(1024 * 1024)
                .addOnSuccessListener {
                    val profileBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    actionBar!!.setIcon(profileBitmap.toDrawable(resources))
                }
        }
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sign_out, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_sign_out -> auth.signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setAuthStateListener(){
        auth.addAuthStateListener {
            if (auth.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    fun hideTabLayout(){
        tabLayout.visibility = View.GONE
    }

    fun showTabLayout(){
        tabLayout.visibility = View.VISIBLE
    }

}
