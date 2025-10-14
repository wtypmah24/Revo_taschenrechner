# Taschenrechner

**Taschenrechner** is a simple Android calculator app built with **Kotlin** and **Jetpack Compose
**.  
It supports both basic arithmetic and scientific functions.
---

## Features

- Basic operations: `+`, `-`, `*`, `/`, `%`, `^`
- Scientific functions: `sin`, `cos`, `tan`, `log`, `ln`, `√`
- Calculation history
- Safe expression parsing with parentheses
- Voice input button (currently shows “Not implemented yet” Toast)
- Built entirely with **Jetpack Compose (Material 3)**

---

## Tech Stack

- **Kotlin**
- **Jetpack Compose (Material 3)**
- **AndroidX Lifecycle & ViewModel**
- **StateFlow** for reactive state management
- **JUnit / kotlin.test** for unit testing

---

## Android & SDK Requirements

| Parameter         | Value                      |
|-------------------|----------------------------|
| **minSdk**        | 24                         |
| **targetSdk**     | 36                         |
| **compileSdk**    | 36                         |
| **jvmTarget**     | 11                         |
| **ApplicationId** | com.example.taschenrechner |
| **VersionCode**   | 1                          |
| **VersionName**   | 1.0                        |

---

## Project Structure

app/
├─ ui/ # Composables (UI)
│ ├─ CalculatorScreen.kt
│ ├─ CalculatorButtonRow.kt
│ └─ ScientificButtons.kt
├─ viewmodel/
│ └─ CalculatorViewModel.kt
├─ engine/
│ └─ CalculatorEngine.kt
├─ data/
│ └─ UiState.kt
├─ MainActivity.kt
└─ build.gradle.kts

## Build & Run

### Prerequisites

- **Android Studio Dolphin** or newer
- **JDK 11**
- **Gradle 8+** (usually bundled with Android Studio)

### Steps

1. Clone the repository:

```bash
git clone https://github.com/yourusername/Taschenrechner.git
cd Taschenrechner
```

Open the project in Android Studio.

Build the project:

```bash
./gradlew build
```

Run on an emulator or physical device with API 24+.

(Optional) Run unit tests:

```bash
./gradlew test
```
