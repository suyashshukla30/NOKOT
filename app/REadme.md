                            +--------------------------------+
                            |        User Launches App       |
                            +--------------------------------+
                                       |
                               Check if User is Logged In
                                       |
                        +--------------+---------------+
                        |                              |
                +---------------+              +---------------------+
                | Not Logged In |              | Logged In           |
                +---------------+              +---------------------+
                        |                              |
                    Show LoginScreen                 Show NotesListScreen
                        |                              |
    +-------------------+------------------------------+------------------+
    |                                                               |
    |   If internet is available, sync notes with Firebase           |
    |   If offline, fetch notes from Room DB                         |
    |                                                               |
+---------------+   +----------------+    +--------------------+
| Firebase Auth |   | Fetch from Room |    | Firebase Firestore  |
|   Login       |   | DB if Offline   |    | Sync (Add, Edit, Delete)|
+---------------+   +----------------+    +--------------------+
|                       |                    |
+-------------+   +-------------------+   +--------------------+
|   Success   |   |  Show Notes List  |   |    Upload/Delete     |
+-------------+   +-------------------+   +--------------------+
|                       |                    |
+---------------+  +-------------------+  +---------------------+
| Add/Edit Note |  |  Sync with Firebase|  |   Show Notes List   |
+---------------+  +-------------------+  +---------------------+
|                     |
Add Image Feature         Delete Note Feature
|
Save Image in Firebase Storage
