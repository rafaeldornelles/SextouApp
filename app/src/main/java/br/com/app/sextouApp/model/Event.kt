package br.com.app.sextouApp.model

import androidx.activity.OnBackPressedDispatcherOwner
import java.util.*

data class Event(val id:String, var date: Calendar,var ownerEmail:String,var ownerId:String, var status:String) {
}