# CookBook 🍳

A modern Android application for managing your personal collection of recipes, keeping track of your pantry, and organizing your shopping list.

## ✨ Features

- **Recipe Management**: 
    - Create new recipes with a name, description, and list of ingredients.
    - Attach photos or videos to your recipes to visually document your dishes.
    - View recipe details including a media preview and formatted ingredients list.
    - Search through your recipe collection using a real-time filter.
- **Shopping List**:
    - Add items you need to buy.
    - Mark items as checked once purchased.
    - **One-click Checkout**: Move all checked shopping items directly to your pantry.
- **Pantry Tracker**:
    - Maintain a list of ingredients currently available in your kitchen.
    - Easily add or remove items from your pantry.

## 🛠 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Database**: [Room](https://developer.android.com/training/data-storage/room) (SQLite abstraction)
- **Media Loading**: [Coil](https://coil-kt.github.io/coil/) (including video frame decoding for thumbnails)
- **Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Architecture**: MVVM (Model-View-ViewModel)

## 🚀 Getting Started

### Prerequisites
- Android Studio (Ladybug or newer recommended)
- JDK 17+

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/CookBook.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or a physical Android device.

---
Developed as a personal project to organize culinary inspiration.
