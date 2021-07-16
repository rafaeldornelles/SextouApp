package br.com.app.sextouApp.util.db

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseClient private constructor() {

    companion object {
        lateinit var db: FirebaseFirestore

        fun getFirebase(): FirebaseFirestore {
            if (Companion::db.isInitialized) {
                db = Firebase.firestore
            }
            return db
        }
    }
}