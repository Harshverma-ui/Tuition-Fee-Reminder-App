# Fee Reminder App

A modern Android application built using Kotlin and Jetpack Compose to help tuition teachers manage students, track fee payments, send reminders, and organize records efficiently.

---

## Features

- Add, Edit and Delete Students
- Mark Fee as Paid / Unpaid
- Automatic Next Due Date Calculation
- Daily Fee Reminder Notifications (WorkManager)
- Dashboard with Student Statistics
- Search Students
- Batch-wise Filtering
- Excel Bulk Import
- Duplicate Student Detection During Import
- Collection Date
- Admission Date
- Student Details Screen
- Settings Screen

---

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Room Database
- MVVM Architecture
- Repository Pattern
- WorkManager
- Apache POI (Excel Import)
- AndroidX Navigation Compose
- Kotlin Coroutines
- StateFlow

---

## Libraries Used

| Library | Purpose |
|---------|---------|
| Jetpack Compose | Modern UI Development |
| Material 3 | UI Components |
| Room Database | Local Database |
| Navigation Compose | Screen Navigation |
| Lifecycle ViewModel | UI State Management |
| Kotlin Coroutines | Background Operations |
| StateFlow | Reactive UI Updates |
| WorkManager | Daily Fee Reminder Notifications |
| Apache POI | Read Excel (.xlsx) Files |

---

## Project Structure

```
FeeReminder
│
├── data
│   ├── Student.kt
│   ├── StudentDao.kt
│   ├── AppDatabase.kt
│
├── repository
│   └── StudentRepository.kt
│
├── viewmodel
│   └── StudentViewModel.kt
│
├── ui
│   ├── screens
│   ├── navigation
│   ├── components
│
├── notifications
│   ├── FeeReminderWorker.kt
│   ├── NotificationHelper.kt
│
├── excel
│   └── ExcelImporter.kt
```

---

# Architecture

This project follows the **MVVM (Model-View-ViewModel)** Architecture.

```
User

↓

Jetpack Compose UI

↓

ViewModel

↓

Repository

↓

Room Database
```

### Model
Contains all database-related classes.

- Student
- StudentDao
- AppDatabase

### View
Contains all Jetpack Compose Screens.

- Dashboard
- Students
- Student Details
- Add Student
- Edit Student
- Settings

### ViewModel
Handles business logic and communicates with the Repository.

### Repository
Acts as a bridge between the ViewModel and the Room Database.

---

## Project Workflow

### Add Student

```
User

↓

Add Student Screen

↓

StudentViewModel

↓

Repository

↓

Room Database
```

---

### Mark Fee Paid

```
Student Details

↓

Mark Fee Paid

↓

Update Student

↓

Room Database

↓

Dashboard Updates Automatically
```

---

### Excel Import

```
Excel File

↓

Apache POI

↓

ExcelImporter

↓

StudentViewModel

↓

Repository

↓

Room Database
```

Duplicate students are automatically ignored during import.

---

## Database

Student Information includes:

- Name
- Class
- Batch
- Phone Number
- Monthly Fee
- Collection Date
- Admission Date
- Last Paid Date
- Next Due Date
- Fee Status (Paid / Pending)

---

## Notification System

Daily reminders are managed using **WorkManager**.

The app automatically checks students whose fees are due and sends notifications.

---

## Screens

- Dashboard
- Students
- Student Details
- Add Student
- Edit Student
- Settings

---

## Future Improvements

- PDF Report Export
- Backup & Restore
- Firebase Cloud Sync
- Attendance Management
- Fee History
- Dark Mode

---

## Author
**Harsh Verma**
B.Tech Computer Science Engineering
Android Developer | Kotlin | Jetpack Compose
---

## License
This project is created for educational and learning purposes.
