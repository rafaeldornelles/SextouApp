package br.com.app.sextouApp.repository

import android.content.ContentValues
import android.util.Log
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
                val member = it.first().let {
                    Member(
                        it.id,
                        it.get(Member.MemberConstants.NAME).toString(),
                        it.get(Member.MemberConstants.EMAIL).toString(),
                        it.get(Member.MemberConstants.PHOTO)?.toString()
                    )
                }
                listener.onSuccess(member)
            }.addOnFailureListener{
                listener.onError("Não foi possível encontrar o membro.")
            }
    }

    fun update(member: Member, listener: ListenerCrudFirebase){
        db.collection(Member.MemberConstants.COLLECTION)
            .document(member.id)
            .update(member.toMap())
            .addOnSuccessListener {
                listener.onSuccess()
            }
            .addOnFailureListener {
                listener.onError()
            }
    }

}