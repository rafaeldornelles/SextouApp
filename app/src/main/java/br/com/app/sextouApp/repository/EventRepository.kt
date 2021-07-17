package br.com.app.sextouApp.repository

import android.content.ContentValues.TAG
import android.util.Log
import br.com.app.sextouApp.model.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EventRepository {
    var db = Firebase.firestore

    fun listEvent(listenerList: ListenerList<Event>, email: String?) {
        db.collection(Event.EventConstants.COLLECTION)
            .whereArrayContainsAny(Event.EventConstants.MEMBERS, listOf(email))
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    listenerList.onError(e.message)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.metadata.hasPendingWrites()) {
                    var eventsReload: List<Event> = snapshot.documents.map { it ->
                        Event(
                            it.id, it.get(Event.EventConstants.NAME).toString(),
                            it.get(Event.EventConstants.DATE).toString(),
                            it.get(Event.EventConstants.OWNEREMAIL).toString(),
                            it.get(Event.EventConstants.OWNERID).toString(),
                            it.get(Event.EventConstants.STATUS) as Boolean,
                            it.get(Event.EventConstants.MEMBERS) as ArrayList<String>,
                            arrayListOf()
                        )
                    }
                    listenerList.onReload(eventsReload)
                }

                if (snapshot != null && !snapshot.isEmpty) {

                    var eventsSuccess: List<Event> = snapshot.documents.map { it ->
                        print(it.get(Event.EventConstants.STATUS))
                        Event(
                            it.id, it.get(Event.EventConstants.NAME).toString(),
                            it.get(Event.EventConstants.DATE).toString(),
                            it.get(Event.EventConstants.OWNEREMAIL).toString(),
                            it.get(Event.EventConstants.OWNERID).toString(),
                            it.get(Event.EventConstants.STATUS) as Boolean,
                            it.get(Event.EventConstants.MEMBERS) as ArrayList<String>,
                            arrayListOf()
                        )
                    }
                    listenerList.onSuccess(eventsSuccess)
                } else {
                    Log.d(TAG, " data: null")
                }
            }
    }

    fun save(event: Event, listener: ListenerCrudFirebase) {
        db.collection(Event.EventConstants.COLLECTION).document().set(event.toMap())
            .addOnSuccessListener {
                Log.d(TAG, "Evento salvo")
                listener.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error ao salvar evento", e)
                listener.onError()
            }
    }

    fun update(event: Event,listener: ListenerCrudFirebase) {
        db.collection(Event.EventConstants.COLLECTION).document(event.id!!).set(event.toMap())
            .addOnSuccessListener {
                Log.d(TAG, "Evento Atualizado")
                listener.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error ao atualizar evento", e)
                listener.onError()
            }
    }

}