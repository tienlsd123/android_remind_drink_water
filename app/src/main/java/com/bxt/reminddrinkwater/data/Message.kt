package com.bxt.reminddrinkwater.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "message")
data class Message(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    val time: Long = System.currentTimeMillis(),
    val content: String = ""
)
