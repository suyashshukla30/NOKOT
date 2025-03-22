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

**App ScreenShots**

![Screenshot_20250322_162710_NOKOT.jpg](..%2F..%2FDownloads%2FScreenshot_20250322_162710_NOKOT.jpg)
![Screenshot_20250322_162410_NOKOT.jpg](..%2F..%2FDownloads%2FScreenshot_20250322_162410_NOKOT.jpg)
![Screenshot_20250322_162320_NOKOT.jpg](..%2F..%2FDownloads%2FScreenshot_20250322_162320_NOKOT.jpg)
![Screenshot_20250322_162451_NOKOT.jpg](..%2F..%2FDownloads%2FScreenshot_20250322_162451_NOKOT.jpg)

*App Link : https://drive.google.com/file/d/1Mrwm_WmNWxzeRbzyv-pJO2YV2CvHqQAv/view?usp=sharing*

_Hustling Again!!_