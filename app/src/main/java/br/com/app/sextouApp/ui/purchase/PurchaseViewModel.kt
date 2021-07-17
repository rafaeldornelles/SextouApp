package br.com.app.sextouApp.ui.purchase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.app.sextouApp.model.Purchases
import br.com.app.sextouApp.utils.Validator

class PurchaseViewModel: ViewModel() {
    val purchases = MutableLiveData(mutableListOf(
        Purchases("0", "aaa", "kk", true),
        Purchases("2", "ccc", "k2k", true),
        Purchases("1", "bbb", "k3k", false)
    ))

    val members = MutableLiveData(mutableListOf(
        "rafafd@hotmail.com",
        "admin@admin.com",
        "johndoe@gmail.com"
    ))

    val itemFormId = MutableLiveData<String>()
    val itemFormName = MutableLiveData<String>()
    val itemFormInfo = MutableLiveData<String>()
    val memberFormId = MutableLiveData<Int?>()
    val memberFormEmail = MutableLiveData<String>()

    fun validateItemForm(): Boolean{
        return this.itemFormName.value?.isBlank() == false
    }

    // TODO: INSERIR NO FIREBASE
    fun submitItemForm(){
        val purchase = Purchases("",
            itemFormName.value!!,
            itemFormInfo.value,
            false)

        val purchasesValue = this.purchases.value?: mutableListOf()
        if (this.itemFormId.value.isNullOrBlank()){
            purchasesValue.add(purchase)
        }else{
            purchasesValue.indexOfFirst{ it.id == itemFormId.value }.apply {
                purchasesValue[this] = purchase
            }
        }
        this.purchases.value = purchasesValue
        this.itemFormName.value = ""
        this.itemFormInfo.value = ""
        this.itemFormId.value = null
    }

    fun validateMemberForm(): Boolean{
        return Validator.EMAIL.validate(memberFormEmail.value) == null
    }

    //TODO: INTEGRAR COM FIREBASE
    fun submitMemberForm(){
            val member = this.memberFormEmail.value!!
            val membersValue = this.members.value ?: mutableListOf()
        if (this.memberFormId.value == null){
            membersValue.add(member)
        }else{
            membersValue[memberFormId.value!!] = member
        }
            this.members.value = membersValue
            this.memberFormEmail.value = ""
            this.memberFormId.value = null
    }

    fun setItemFormValue(item: Purchases) {
        this.itemFormName.value = item.nome
        this.itemFormInfo.value = item.info
        this.itemFormId.value = item.id
    }

    fun setMemberFormValue(member: String, position: Int) {
        memberFormId.value = position
        memberFormEmail.value = member
    }
}