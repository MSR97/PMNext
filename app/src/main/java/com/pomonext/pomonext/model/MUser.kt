package com.pomonext.pomonext.model

data class MUser(
    val id: String? = null,
    val userId: String,
    val userEmail: String = "",
    val name: String = "",
    val avatarUrl: String = "",
    val profileCaption: String = "",
    val profession: String = ""
) {


    fun toUserMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "userEmail" to this.userEmail,
            "name" to this.name,
            "avatarUrl" to this.avatarUrl,
            "profileCaption" to this.profileCaption,
            "profession" to this.profession
        )
    }
}
