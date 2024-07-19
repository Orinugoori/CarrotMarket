package com.example.carrotmarketclone

data class Event(val value: Int) {
    val data: Int
        get() = value / 10

    val isHeader: Boolean
        get() = value % 10 == 0

}
