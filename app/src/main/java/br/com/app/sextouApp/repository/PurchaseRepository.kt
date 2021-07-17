package br.com.app.sextouApp.repository

import android.content.ContentValues
import android.util.Log
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.model.Purchases
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PurchaseRepository {
    var db = Firebase.firestore

    fun listPurchases(listenerList: ListenerList<Purchases>, eventId: String) {
        db.collection(Event.EventConstants.COLLECTION)
            .document(eventId)
            .collection(Event.EventConstants.PURCHASES)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    listenerList.onError(e.message)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.metadata.hasPendingWrites()) {
                    var purchasesReload: List<Purchases> = snapshot.documents.map { it ->
                        Purchases(
                            it.id,
                            it.get(Purchases.PurchaseConstants.NOME).toString(),
                            it.get(Purchases.PurchaseConstants.INFO).toString(),
                            it.get(Purchases.PurchaseConstants.PURCHASED) as Boolean
                        )
                    }
                    listenerList.onReload(purchasesReload)
                }

                if (snapshot != null && !snapshot.isEmpty) {

                    var purchasesSuccess: List<Purchases> = snapshot.documents.map { it ->
                        print(it.get(Event.EventConstants.STATUS))
                        Purchases(
                            it.id,
                            it.get(Purchases.PurchaseConstants.NOME).toString(),
                            it.get(Purchases.PurchaseConstants.INFO).toString(),
                            it.get(Purchases.PurchaseConstants.PURCHASED) as Boolean
                        )
                    }
                    listenerList.onSuccess(purchasesSuccess)
                } else {
                    Log.d(ContentValues.TAG, " data: null")
                }
            }
    }


    fun save(purchase: Purchases, listener: ListenerCrudFirebase, eventId: String) {
        db.collection(Event.EventConstants.COLLECTION)
            .document(eventId)
            .collection(Event.EventConstants.PURCHASES)
            .document()
            .set(purchase.toMap())
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Item salvo")
                listener.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error ao salvar Item", e)
                listener.onError()
            }
    }

    fun update(purchase: Purchases,listener: ListenerCrudFirebase, eventId: String) {
        db.collection(Event.EventConstants.COLLECTION)
            .document(eventId)
            .collection(Event.EventConstants.PURCHASES)
            .document(purchase.id)
            .set(purchase.toMap())
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Item Atualizado")
                listener.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error ao atualizar item", e)
                listener.onError()
            }
    }
}