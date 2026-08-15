# 🌤️ My Weather

> A clean and simple Android weather application that retrieves real-time weather information for cities using Retrofit and a REST API.

## 📱 App Screenshots

| Home Screen                                             | Clouds Screen                                    | Splash Screen                                    
| -------------------------------------------------- | -------------------------------------------------- | -------------------------------------------------- |
| <img src="app/Screenshots/Home Screen.jpeg" width="250"/> | <img src="app/Screenshots/Clouds Screen.jpeg" width="250"/> | <img src="app/Screenshots/Splash Screen.jpeg" width="250"/> |

## 📝 Description

**My Weather** is an Android application built with **Kotlin and XML** that allows users to search for cities and view their weather information.

The project demonstrates practical Android networking concepts by consuming a weather **REST API using Retrofit** and exposing the retrieved data to the UI through **LiveData**.

## ✨ Key Features

* 🔎 **City Search** – Search weather information by city
* 🌤️ **Weather Information** – Display current weather details
* 🌐 **REST API Integration** – Retrieve data from a remote weather service
* ⚡ **Retrofit Networking** – Clean HTTP API communication
* 🔄 **LiveData** – Observe asynchronous data changes
* 🎨 **XML UI** – Traditional Android View-based interface
* ⚠️ **Error Handling** – Handle unsuccessful API requests and invalid searches

## 🛠️ Tech Stack

| Technology      | Purpose                      |
| --------------- | ---------------------------- |
| **Kotlin**      | Primary programming language |
| **XML**         | Android UI                   |
| **Retrofit**    | REST API communication       |
| **LiveData**    | Observable UI data           |
| **Android SDK** | Application platform         |

## ⚙️ Architecture

The application follows a simple separation of UI, data retrieval and presentation logic.

```text
UI
 │
 ▼
ViewModel
 │
 ▼
Repository
 │
 ▼
Retrofit
 │
 ▼
Weather REST API
```

## 🚀 Getting Started

### Prerequisites

* Android Studio
* JDK compatible with the project
* Android SDK
* Internet connection
* Weather API key

### Installation

1. Clone the repository:

```bash
git clone https://github.com/Abdulrehman-dev95/My-Weather.git
```

2. Open the project in Android Studio.

3. Add your weather API credentials using a secure local configuration.

4. Sync Gradle.

5. Build and run the application.

## 🤝 Contributing

Suggestions and improvements are welcome.

Feel free to open an issue or submit a pull request.

## 📄 License

This project is available under the MIT License.
