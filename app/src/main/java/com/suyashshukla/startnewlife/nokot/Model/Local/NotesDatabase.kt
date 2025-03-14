package com.suyashshukla.startnewlife.nokot.Model.Local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NotesDataClass::class], version = 1, exportSchema = true)
abstract class NotesDatabase :RoomDatabase(){
    abstract fun NotesDao(): NotesDao
}