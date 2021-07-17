package br.com.app.sextouApp.model

import android.content.ContentValues
import android.util.Log
import br.com.app.sextouApp.repository.ListenerList

class Member(val id: String, val name: String, val email:String, val photoUrl: String?) {
    object MemberConstants{
        const val COLLECTION = "members"
        const val NAME = "name"
        const val EMAIL = "email"
        const val PHOTO = "photoUrl"
    }

    fun toMap(): HashMap<String, Any?>{
        return hashMapOf(
            MemberConstants.NAME to name,
            MemberConstants.EMAIL to email,
            MemberConstants.PHOTO to photoUrl
        )
    }
}