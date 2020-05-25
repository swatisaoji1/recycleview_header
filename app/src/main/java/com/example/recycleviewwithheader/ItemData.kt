package com.example.recycleviewwithheader

interface ItemData {
    fun getType() : ItemType
}

enum class ItemType {
    HEADER,
    EVENT,
    FIRST_EVENT,
}

data class NonHeaderData(val title: String, val subtitle: String, val iconRes : Int) : ItemData {
    override fun getType() = ItemType.EVENT
}

data class HeaderData(val date: String, val temperature: String, val tempIcon : Int) : ItemData {
    override fun getType() = ItemType.HEADER
}

