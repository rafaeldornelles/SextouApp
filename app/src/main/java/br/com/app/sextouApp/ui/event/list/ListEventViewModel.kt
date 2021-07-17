package br.com.app.sextouApp.ui.event.list

import androidx.lifecycle.ViewModel
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.repository.EventRepository
import br.com.app.sextouApp.repository.ListenerCrudFirebase
import br.com.app.sextouApp.repository.ListenerList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListEventViewModel : ViewModel(){

    val user = Firebase.auth.currentUser
    val eventRepository: EventRepository = EventRepository()

    fun listEvent(listener: ListenerList<Event>){
        eventRepository.listEvent(listener, user?.email!!);
    }

    fun deleteEvent(event: Event, listenerCrudFirebase: ListenerCrudFirebase) {
        event.members.remove(user?.email)
        eventRepository.update(event,listenerCrudFirebase)
    }

}