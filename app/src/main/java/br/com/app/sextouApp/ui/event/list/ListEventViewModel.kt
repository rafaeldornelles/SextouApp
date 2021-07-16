package br.com.app.sextouApp.ui.event.list

import androidx.lifecycle.ViewModel
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.repository.EventRepository
import br.com.app.sextouApp.repository.ListenerList

class ListEventViewModel : ViewModel(){

    val eventRepository: EventRepository = EventRepository()

    fun listEvent(listener: ListenerList<Event>){
        eventRepository.listEvent(listener);
    }

}