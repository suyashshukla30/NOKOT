package com.suyashshukla.startnewlife.nokot.Root

import android.app.Application
import androidx.room.Room
import com.suyashshukla.startnewlife.nokot.Model.Local.NotesDatabase

class NotesApplication: Application() {
    lateinit var database: NotesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java, "note_database"
        ).build()
    }
}