package br.com.app.sextouApp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import br.com.app.sextouApp.model.Member
import br.com.app.sextouApp.repository.Listener
import br.com.app.sextouApp.repository.ListenerCrudFirebase
import br.com.app.sextouApp.repository.MemberRepository
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
        val user = auth.currentUser!!
        val member = Member(auth.uid!!, user.displayName?: user.email!!, user.email!!, auth.currentUser?.photoUrl?.toString())
        MemberRepository().update(member, this.memberListener)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
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

    val memberListener = object : ListenerCrudFirebase{
        override fun onError() {
            Log.e("FIREBASE", "Error in sync user")
        }

        override fun onSuccess() {
            Log.i("FIREBASE", "User sync successful")
        }

    }

}
