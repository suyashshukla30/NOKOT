package com.suyashshukla.startnewlife.nokot.Model.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NotesDataClass(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
