package br.com.app.sextouApp.ui.purchase

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import br.com.app.sextouApp.R
import br.com.app.sextouApp.model.Member
import br.com.app.sextouApp.model.Purchases
import br.com.app.sextouApp.repository.*
import br.com.app.sextouApp.utils.Validator

class PurchaseViewModel: ViewModel() {
    var eventId: String = ""
    val purchaseRepository = PurchaseRepository()
    val memberRepository = MemberRepository()
    val purchases = MutableLiveData<MutableList<Purchases>>(mutableListOf())

    val members = MutableLiveData<MutableList<Member>>(mutableListOf())

    val itemFormId = MutableLiveData<String>()
    val itemFormName = MutableLiveData<String>()
    val itemFormInfo = MutableLiveData<String>()
    val memberFormId = MutableLiveData<Int?>()
    val memberFormEmail = MutableLiveData<String>()

    fun validateItemForm(): Boolean{
        return this.itemFormName.value?.isBlank() == false
    }

    fun submitItemForm(listener: ListenerCrudFirebase){
        val purchase = Purchases(itemFormId.value?:"",
            itemFormName.value!!,
            itemFormInfo.value,
            false)

        if (this.itemFormId.value.isNullOrBlank()){
            purchaseRepository.save(purchase, listener, eventId)
        }else{
            purchaseRepository.update(purchase, listener, eventId)
        }
        this.itemFormName.value = ""
        this.itemFormInfo.value = ""
        this.itemFormId.value = null
    }

    fun validateMemberForm(): Boolean{
        return Validator.EMAIL.validate(memberFormEmail.value) == null
    }

    fun submitMemberForm(onSearchError: ()->Unit, onAddListenerCrudFirebase: ListenerCrudFirebase){
        val member = this.memberFormEmail.value!!
        this.memberRepository.findByEmail(this.memberFormEmail.value!!, object : Listener<Member> {
            override fun onError(message: String?) {
                onSearchError()
            }
            override fun onSuccess(data: Member) {
                memberRepository.addToEvent(data, eventId, onAddListenerCrudFirebase)
            }

        })
        this.memberFormEmail.value = ""
        this.memberFormId.value = null
    }

    fun setItemFormValue(item: Purchases) {
        this.itemFormName.value = item.nome
        this.itemFormInfo.value = item.info
        this.itemFormId.value = item.id
    }

    fun setMemberFormValue(member: Member, position: Int) {
        memberFormId.value = position
        memberFormEmail.value = member.email
    }

    fun markPurchased(position: Int, listener: ListenerCrudFirebase) {
        val purchasesValue = this.purchases.value?: return
        val changedPurchase = purchasesValue[position]
        changedPurchase.purchased = true
        this.purchaseRepository.update(changedPurchase, listener, eventId)
    }

    fun listPurchases(){
        val listener = object : ListenerList<Purchases>{
            override fun onError(message: String?) {
                Log.e("ERROR", "Error retrieving data from firebase")
            }

            override fun onSuccess(list: List<Purchases>) {
                purchases.value = list.toMutableList()
            }

            override fun onReload(list: List<Purchases>) {
                purchases.value = list.toMutableList()
            }
        }

        if (eventId.isNotBlank()){
            this.purchaseRepository.listPurchases(listener, eventId)
        }
    }

    fun listMembers(){
        val listener = object : ListenerList<Member>{
            override fun onError(message: String?) {
                Log.e("FIREBASE", "Erro ao carregar membros $message")
            }

            override fun onSuccess(list: List<Member>) {
                members.value = list.toMutableList()
            }

            override fun onReload(list: List<Member>) {
                members.value = list.toMutableList()
            }

        }
        this.memberRepository.listMembersInEvent(eventId, listener)
    }

}