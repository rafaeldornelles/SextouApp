package br.com.app.sextouApp.ui.event.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.repository.EventRepository
import br.com.app.sextouApp.repository.ListenerCrudFirebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EventFormViewModel : ViewModel() {

    private val user = Firebase.auth.currentUser
    private val eventRepository: EventRepository = EventRepository()
    val name = MutableLiveData<String>()


    fun save(date: String, listener : ListenerCrudFirebase) {
        var eventDate = if(date.isEmpty()) "Sem data" else date
        var event =  Event(null,
            name.value.toString(),
            eventDate,
            user?.email!!,
            user?.uid!!,
            false,
            arrayListOf(user?.email!!),
            arrayListOf())

        eventRepository.save(event,listener)
    }

}