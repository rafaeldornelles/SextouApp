package br.com.app.sextouApp.model

class Purchases (val id:String, var nome:String, var info: String?, var purchased:Boolean){
    object PurchaseConstants{
        const val COLLECTION = "purchases"
        const val NOME = "nome"
        const val PURCHASED = "purchased"
        const val INFO = "info"
    }

    fun toMap(): HashMap<String, Any?>{
        return hashMapOf(
            PurchaseConstants.NOME to nome,
            PurchaseConstants.PURCHASED to purchased,
            PurchaseConstants.INFO to info
        )
    }
}
