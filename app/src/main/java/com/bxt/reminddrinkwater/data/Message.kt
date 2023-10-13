package com.bxt.reminddrinkwater.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    var time: Long = System.currentTimeMillis(),
    var content: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
