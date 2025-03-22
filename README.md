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

![Screenshot_20250322_162410_NOKOT](https://github.com/user-attachments/assets/67a2e21f-44d0-471e-968b-b6e209c32b59)
![Screenshot_20250322_162320_NOKOT](https://github.com/user-attachments/assets/18284720-5967-4da7-8523-544b3ef2facf)
![Screenshot_20250322_162710_NOKOT](https://github.com/user-attachments/assets/b96e1e27-6d4e-42b9-8883-dddf5b89e152)
![Screenshot_20250322_162451_NOKOT](https://github.com/user-attachments/assets/23893261-792e-4590-b0c6-4116c8764f37)

*App Link : https://drive.google.com/file/d/1Mrwm_WmNWxzeRbzyv-pJO2YV2CvHqQAv/view?usp=sharing*

_Hustling Again!!_
