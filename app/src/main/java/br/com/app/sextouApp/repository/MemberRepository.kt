package br.com.app.sextouApp.repository

import android.content.ContentValues
import android.util.Log
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.model.Member
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MemberRepository {
    var db = Firebase.firestore

    fun listMembers(memberList: ListenerList<Member>) {
        db.collection(Member.MemberConstants.COLLECTION)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    memberList.onError(e.message)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.metadata.hasPendingWrites()) {
                    var membersReload: List<Member> = snapshot.documents.map { it ->
                        Member(
                            it.id,
                            it.get(Member.MemberConstants.NAME).toString(),
                            it.get(Member.MemberConstants.EMAIL).toString(),
                            it.get(Member.MemberConstants.PHOTO)?.toString()
                        )
                    }
                    memberList.onReload(membersReload)
                }

                if (snapshot != null && !snapshot.isEmpty) {

                    var membersSuccess: List<Member> = snapshot.documents.map { it ->
                        Member(
                            it.id,
                            it.get(Member.MemberConstants.NAME).toString(),
                            it.get(Member.MemberConstants.EMAIL).toString(),
                            it.get(Member.MemberConstants.PHOTO)?.toString()
                        )
                    }
                    memberList.onSuccess(membersSuccess)
                } else {
                    Log.d(ContentValues.TAG, " data: null")
                }
            }
    }

    fun save(member: Member, listener: ListenerCrudFirebase, eventId: String) {
        db.collection(Member.MemberConstants.COLLECTION)
            .document()
            .set(member.toMap())
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Item salvo")
                listener.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error ao salvar Item", e)
                listener.onError()
            }
    }

    fun update(member: Member, listener: ListenerCrudFirebase, eventId: String) {
        db.collection(Member.MemberConstants.COLLECTION)
            .document(member.id)
            .set(member.toMap())
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Item Atualizado")
                listener.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error ao atualizar item", e)
                listener.onError()
            }
    }

    fun findByEmail(email: String, listener: Listener<Member>){
        db.collection(Member.MemberConstants.COLLECTION)
            .whereEqualTo(Member.MemberConstants.EMAIL, email)
            .get().addOnSuccessListener {
                it.firstOrNull()?.let {
                    val member = Member(
                        it.id,
                        it.get(Member.MemberConstants.NAME).toString(),
                        it.get(Member.MemberConstants.EMAIL).toString(),
                        it.get(Member.MemberConstants.PHOTO)?.toString()
                    )
                    listener.onSuccess(member)
                } ?: listener.onError("Email not found")
            }.addOnFailureListener{
                listener.onError("Não foi possível encontrar o membro.")
            }
    }

    fun update(member: Member, listener: ListenerCrudFirebase){
        db.collectionGroup(Member.MemberConstants.COLLECTION)
            .whereEqualTo(Member.MemberConstants.EMAIL, member.email)
            .get().addOnSuccessListener {
                it.documents.forEach{
                    it.reference.set(member.toMap())
                }
            }
        db.collection(Member.MemberConstants.COLLECTION)
            .document(member.id).set(member.toMap()).addOnSuccessListener {
                listener.onSuccess()
            }.addOnFailureListener {
                listener.onError()
            }
    }

    fun addToEvent(member: Member, eventId:String, listener: ListenerCrudFirebase){
        db.collection(Event.EventConstants.COLLECTION)
            .document(eventId)
            .get().addOnSuccessListener {
                val event = Event(
                    it.id, it.get(Event.EventConstants.NAME).toString(),
                    it.get(Event.EventConstants.DATE).toString(),
                    it.get(Event.EventConstants.OWNEREMAIL).toString(),
                    it.get(Event.EventConstants.OWNERID).toString(),
                    it.get(Event.EventConstants.STATUS) as Boolean,
                    it.get(Event.EventConstants.MEMBERS) as ArrayList<String>,
                    arrayListOf()
                )
                if (event.members.none{ m -> m==member.email}){
                    event.members.add(member.email)
                }
                it.reference.set(event.toMap()).addOnSuccessListener {
                    listener.onSuccess()
                }.addOnFailureListener {
                    listener.onError()
                }
            }.addOnFailureListener {
                listener.onError()
            }
    }

    fun listMembersInEvent(eventId: String, listenerList: ListenerList<Member>){
        db.collection(Event.EventConstants.COLLECTION)
            .document(eventId)
            .get().addOnSuccessListener {
                val event = Event(
                    it.id, it.get(Event.EventConstants.NAME).toString(),
                    it.get(Event.EventConstants.DATE).toString(),
                    it.get(Event.EventConstants.OWNEREMAIL).toString(),
                    it.get(Event.EventConstants.OWNERID).toString(),
                    it.get(Event.EventConstants.STATUS) as Boolean,
                    it.get(Event.EventConstants.MEMBERS) as ArrayList<String>,
                    arrayListOf()
                )
                db.collection(Member.MemberConstants.COLLECTION)
                    .whereIn(Member.MemberConstants.EMAIL, event.members)
                    .addSnapshotListener { snapshot, e ->

                        if (e != null) {
                            listenerList.onError(e.message)
                            return@addSnapshotListener
                        }

                        if (snapshot != null && snapshot.metadata.hasPendingWrites()) {
                            var membersReload: List<Member> = snapshot.documents.map { it ->
                                Member(
                                    it.id,
                                    it.get(Member.MemberConstants.NAME).toString(),
                                    it.get(Member.MemberConstants.EMAIL).toString(),
                                    it.get(Member.MemberConstants.PHOTO)?.toString()
                                )
                            }
                            listenerList.onReload(membersReload)
                        }

                        if (snapshot != null && !snapshot.isEmpty) {

                            var membersSuccess: List<Member> = snapshot.documents.map { it ->
                                print(it.get(Event.EventConstants.STATUS))
                                Member(
                                    it.id,
                                    it.get(Member.MemberConstants.NAME).toString(),
                                    it.get(Member.MemberConstants.EMAIL).toString(),
                                    it.get(Member.MemberConstants.PHOTO)?.toString()
                                )
                            }
                            listenerList.onSuccess(membersSuccess)
                        } else {
                            Log.d(ContentValues.TAG, " data: null")
                        }
                    }
            }

    }

}