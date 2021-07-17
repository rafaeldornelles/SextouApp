package br.com.app.sextouApp.ui.event.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class EventFormViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    var data = MutableLiveData<Calendar>()
}