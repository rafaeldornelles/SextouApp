package br.com.app.sextouApp.model

import androidx.activity.OnBackPressedDispatcherOwner
import java.util.*

data class Event(
    val id: String,
    val name: String,
    var date: String,
    var ownerEmail: String,
    var ownerId: String,
    var status: Boolean,
    var members: List<String>,
    var purchases: List<Purchases>
) {


    object EventConstants {
        const val COLLECTION = "event"
        const val NAME = "name"
        const val DATE = "date"
        const val OWNEREMAIL = "ownerEmail"
        const val OWNERID = "ownerId"
        const val STATUS = "status"
        const val MEMBERS = "members"

    }
}