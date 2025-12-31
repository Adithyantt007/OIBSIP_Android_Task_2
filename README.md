# Secure Task Management App - Android (Java/SQLite)

A professional, Material Design-based To-Do List application built during my internship at **Oasis Infobyte**. This app allows users to manage their schedules, meetings, and notes within a secure, authenticated environment.



## 🚀 Features

- **User Authentication:** Secure Login and Sign-up system powered by a local SQLite database.
- **Task Management:** Create, view, and permanently delete tasks or meetings.
- **Persistent Storage:** All data is stored locally using SQLite, ensuring notes survive app restarts.
- **Material Design 3:** Uses modern UI components like `MaterialCardView`, `ExtendedFAB`, and `TextInputLayout`.
- **Intuitive UX:** Includes a native `DatePickerDialog` for scheduling and confirmation prompts for critical actions (Delete/Logout).
- **Responsive Layout:** Edge-to-edge support ensures the UI sits perfectly below device camera notches.

## 🛠️ Technical Stack

- **Language:** Java
- **UI:** XML (Material Design 3)
- **Database:** SQLite (Local)
- **Architecture:** RecyclerView + Custom Adapter Pattern

## 🏗️ Project Structure

- `DatabaseHelper.java`: Manages SQLite schemas for Users and Tasks.
- `LoginActivity.java` & `SignupActivity.java`: Handles user authentication logic.
- `MainActivity.java`: The core task engine and UI controller.
- `TodoAdapter.java`: Optimized RecyclerView adapter for smooth list performance.


## 📦 How to Install
1. Clone the repository.
2. Open the project in **Android Studio**.
3. Ensure you have the Material Components dependency in your `build.gradle`.
4. Run on an emulator or physical device (Android 8.0+ recommended).

---
*Developed as part of the Oasis Infobyte Internship Program.* Visit: [oasisinfobyte.com](https://oasisinfobyte.com/)
