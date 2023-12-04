package com.example.finalproject.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long,
    val sell: String
) {
    constructor(): this("", "","", 0, "")
}
