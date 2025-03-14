package com.suyashshukla.startnewlife.nokot.Model.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.suyashshukla.startnewlife.nokot.Model.Local.NotesDao
import com.suyashshukla.startnewlife.nokot.Model.Local.NotesDataClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class NotesRepository(
    private val noteDao: NotesDao,
    private val firestore: FirebaseFirestore, // Firestore instance
    private val userId: String // User ID for Firestore
) {
    private val notesCollection = firestore.collection("users").document(userId).collection("notes")
    suspend fun addDemoNotesIfNeeded() {
        try {
            val notesCollection = firestore.collection("users").document(userId).collection("notes")
            val snapshot = notesCollection.get().await()
            if (snapshot.isEmpty) {
                val demoNotes = listOf(
                    NotesDataClass(
                        id = 0,
                        title = "Demo Note 1",
                        content = "This is a demo note.",
                        imageUrl = ""
                    ),
                    NotesDataClass(
                        id = 1,
                        title = "Demo Note 2",
                        content = "This is another demo note.",
                        imageUrl = ""
                    ),
                    NotesDataClass(
                        id = 2,
                        title = "Demo Note 3",
                        content = "This is yet another demo note.",
                        imageUrl = ""
                    )
                )
                demoNotes.forEach { note ->
                    notesCollection.document(note.id.toString()).set(note).await()
                }
            }
        } catch (e: Exception){
            e.message;
        }
    }
    // Fetch all notes from local Room database
    suspend fun getAllLocalNotes(): List<NotesDataClass> {
        return noteDao.getAllNotes()
    }

    // Save note to local Room database
    suspend fun saveNoteToLocal(note: NotesDataClass) {
        noteDao.insert(note)
    }

    // Delete note from local Room database
    suspend fun deleteNoteFromLocal(note: NotesDataClass) {
        noteDao.delete(note)
    }

    suspend fun syncNotesFromFirestore() {
        val firestoreNotes = notesCollection.get().await()
        firestoreNotes.documents.mapNotNull { doc ->
            doc.toObject(NotesDataClass::class.java)
        }.forEach { note ->
            noteDao.insert(note)
        }
    }

    suspend fun saveNoteToFirestore(note: NotesDataClass) {
        val noteDocument = notesCollection.document(note.id.toString())
        noteDocument.set(note).await()
    }

    suspend fun deleteNoteFromFirestore(noteId: Int) {
        notesCollection.document(noteId.toString()).delete().await()
    }
}