package com.suyashshukla.startnewlife.nokot.Model.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NotesDataClass)

    @Update
    suspend fun update(note: NotesDataClass)

    @Delete
    suspend fun delete(note: NotesDataClass)

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    suspend fun getAllNotes(): List<NotesDataClass>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): NotesDataClass?
}