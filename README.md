# NYN App

This is a modern Android note-taking app built with Jetpack Compose for a seamless and intuitive user experience. Users can create, edit, and organize notes with titles, bodies, and categories. It offers features like background color customization, bookmarking, and sharing notes with other apps. With search and category filters, managing notes has never been easier!

<img src="https://github.com/med25ch/NYN/blob/master/screenshots/MockApp.png"></img>

## Features

- Create Notes :
Users can create notes with a title and body.

- Categorize Notes :
Add categories to organize notes | Relate each note to a specific category.

- Customize Notes : 
Change the background color of the note box for personalization or organization.

- Share Notes : 
Share notes with other apps via an Android intent.

- Manage Notes : 
Delete notes when they are no longer needed | Update or edit existing notes.

- Search Notes :
Search for notes by keywords in the title or body.

- Filter Notes : 
Filter notes by categories for better organization.

- Bookmark Notes : 
Mark important notes as bookmarks for quick access.

## Tech Stack

UI :
- Jetpack Compose: For building the UI declaratively.
- Material 3: For adhering to modern Material Design guidelines.
- Navigation Component: For seamless navigation between screens.

Architecture :
- Clean Architecture: Separation of concerns through domain, data, and presentation layers.
- MVVM (Model-View-ViewModel): For structuring the app and managing UI-related data.
- ViewModel: For managing UI-related data lifecycle-conscious.

Data & Backend : 
- Room Database: For local data persistence.
- Coroutines & Flow: For managing asynchronous tasks and data streams in a clean, concise way.

Dependency Injection
- Hilt: For managing dependency injection efficiently across the app.

