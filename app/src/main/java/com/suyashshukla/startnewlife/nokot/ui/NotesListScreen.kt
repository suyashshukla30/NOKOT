package com.suyashshukla.startnewlife.nokot.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.annotations.concurrent.Background
import com.google.firebase.auth.FirebaseAuth
import com.suyashshukla.startnewlife.nokot.Model.Local.NotesDataClass
import com.suyashshukla.startnewlife.nokot.R
import com.suyashshukla.startnewlife.nokot.ViewModel.NotesViewModel
import com.suyashshukla.startnewlife.nokot.Views.MainActivity
import com.suyashshukla.startnewlife.nokot.Views.NotesListActivity

/**
 * Main screen that displays the list of notes.
 * @param notesViewModel ViewModel providing the list of notes and handling actions.
 */
@Composable
fun NotesListScreen(notesViewModel: NotesViewModel) {
    // Observing notes and error message state from ViewModel.
    var isLoggingOut by remember { mutableStateOf(false) }
    val notes by notesViewModel.notes.collectAsState(initial = emptyList())
    val errorMessage by notesViewModel.errorMessage.collectAsState()
    val isloading by notesViewModel.isLoading.collectAsState()
    val (showAddDialog, setShowAddDialog) = remember { mutableStateOf(false) }
    val (selectNote, setSelectedNote) = remember { mutableStateOf<NotesDataClass?>(null) }
    val context = LocalContext.current // Get the context

    // Root layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        // Title of the screen
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(horizontal = 5.dp)
        ) {
            Text(
                text = "Your Notes",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { isLoggingOut = true }) {
                Icon(
                    imageVector = Icons.Rounded.ExitToApp,
                    contentDescription = "Logout menu",
                    modifier = Modifier.clickable {
                        val activity = (context as? NotesListActivity)
                        activity?.logoutUser {
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }

//        if(isloading){
//            Loader()
//        }
//        // Display error message if present
//        else {
        if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
//        }

        // Display message if no notes are available
        if (notes.isEmpty()) {
            Text(
                text = stringResource(R.string.no_notes_available_create_your_first_note),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            // Display the list of notes
            NotesList(
                notes = notes,
                onNoteClick = { note -> setSelectedNote(note) },
                onDeleteClick = { note -> notesViewModel.deleteNote(note) }
            )
        }
    }
    if (isLoggingOut) {
        LogoutLoader()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { setShowAddDialog(true) },
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text(
                "+",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    if (showAddDialog) {
        AddNoteDialog(
            onSave = { note ->
                notesViewModel.saveNote(note)
                setShowAddDialog(false)
            },
            onDismiss = { setShowAddDialog(false) }
        )
    }

    if (selectNote != null) {
        EditNoteDialog(
            note = selectNote,
            onDismiss = { setSelectedNote(null) }, // Close dialog
            onSave = { updatedNote ->
                notesViewModel.saveNote(updatedNote) // Save the updated note
                setSelectedNote(null) // Close dialog
            }
        )
    }
}

@Composable

fun Loader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circular Progress Indicator with a size and color configuration
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Loading message
            Text(
                text = "Loading notes...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun AddNoteDialog(onSave: (NotesDataClass) -> Unit, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add New Note",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        if (title.isNotBlank() || content.isNotBlank()) {
                            val newNote = NotesDataClass(
                                id = System.currentTimeMillis().toInt(), // Unique ID for now
                                title = title,
                                content = content,
                                imageUrl = "" // Placeholder for image
                            )
                            onSave(newNote)
                        }
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun EditNoteDialog(
    note: NotesDataClass,
    onDismiss: () -> Unit,
    onSave: (NotesDataClass) -> Unit
) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Note") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(note.copy(title = title, content = content)) }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Placeholder for future sharing functionality.
 */
@Composable
fun ShareNote(notes: List<NotesDataClass>) {
    // This function is currently empty but can be used for additional features
}

/**
 * Displays a scrollable list of notes.
 * @param notes List of notes to display.
 * @param onNoteClick Action to perform when a note is clicked.
 * @param onDeleteClick Action to perform when the delete button is clicked.
 */
@Composable
fun NotesList(
    notes: List<NotesDataClass>,
    onNoteClick: (NotesDataClass) -> Unit,
    onDeleteClick: (NotesDataClass) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Display each note using the NoteItem composable
        items(notes) { note ->
            NoteItem(
                note = note,
                onClick = { onNoteClick(note) },
                onDeleteClick = { onDeleteClick(note) }
            )
        }
    }
}

/**
 * Displays a single note as a card.
 * @param note Note data to display.
 * @param onClick Action to perform when the note is clicked.
 * @param onDeleteClick Action to perform when the delete button is clicked.
 */
@Composable
fun NoteItem(
    note: NotesDataClass,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Makes the card clickable
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display note title
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Ellipsis if the title is too long
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Display note content
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis // Ellipsis if the content is too long
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Row for the delete button
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onDeleteClick) {
                    Text(text = "Delete") // Delete button
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Logging Out") },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun LogoutLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Logging out...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


