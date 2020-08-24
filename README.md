Project Import Instructions:

Start Android Studio and close any open Android Studio projects.

From the Android Studio menu click File > New > Import Project.
Select the destination folder and click Next.
N.B : recommended latest android studio version.

About

This is a simple File downloader app in which the users can easily download and play the media files using URL's.
The App will display the current download progress and can pause and resume the downloading task.
And the users can find the downloaded files in device storage. Also the users can see the downloaded files in Download
 section of the app. Swipe to delete functionality is implemented to delete the media files(Right to Left).
 So in the Downloads Page the users can play and delete the downloaded files.



Architecture Used:

MVVM(Model-View-ViewModel) is the architecture pattern used in this project.

It provides Maintainability, testability, extensibility and, in general, a more clean and maintainable codebase.

Advantages of MVVM

Maintainability:

A clean separation of different kinds of code should make it easier to go into one or several of those more granular and focused parts and make changes without worrying.

That means you can remain agile and keep moving out to new releases quickly.

Testability:

With MVVM each piece of code is more granular and if it is implemented right your external and internal dependences are in separate pieces of code from the parts with the core logic that you would like to test.

That makes it a lot easier to write unit tests against a core logic.

Extensibility :

It sometimes overlaps with maintainability, because of the clean separation boundaries and more granular pieces of code.

We have a better chance of making any of those parts more reusable.

It has also the ability to replace or add new pieces of code that do similar things into the right places in the architecture.

Library Used:

MVVM,Navigation,Coroutines,LiveData,Viewmodel,Room,Glide,PR Downloader,EXO player,Lifecycle etc


********** Thanks for letting me to take part in this test. I was glad i could take it. **********