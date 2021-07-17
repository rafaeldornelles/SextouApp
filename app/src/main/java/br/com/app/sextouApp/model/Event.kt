package br.com.app.sextouApp.model

data class Event(
    val id: String?,
    val name: String,
    var date: String,
    var ownerEmail: String,
    var ownerId: String,
    var status: Boolean,
    var members: ArrayList<String>,
    var purchases: List<Purchases>
) {

    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            EventConstants.NAME to this.name,
            EventConstants.DATE to this.date,
            EventConstants.OWNEREMAIL to this.ownerEmail,
            EventConstants.OWNERID to this.ownerId,
            EventConstants.STATUS to this.status,
            EventConstants.MEMBERS to this.members,
            EventConstants.PURCHASES to this.purchases,
        )
    }

    object EventConstants {
        const val COLLECTION = "event"
        const val NAME = "name"
        const val DATE = "date"
        const val OWNEREMAIL = "ownerEmail"
        const val OWNERID = "ownerId"
        const val STATUS = "status"
        const val MEMBERS = "members"
        const val PURCHASES = "purchases"

    }
}