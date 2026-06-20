# Persian TV - تلویزیون زنده

A lightweight Persian Google TV live streaming app built with Kotlin and Jetpack Compose.

## Features

- Full Persian UI with RTL support
- D-pad optimized navigation for Android TV/Google TV
- HLS, MP4, MPEGTS stream support via ExoPlayer
- Dark/Light theme toggle (تیره/روشن)
- Remember last played channel
- Lightweight and fast startup
- MVVM architecture

## Requirements

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- Kotlin 1.9.22
- Gradle 8.5

## Setup Instructions

### 1. Open in Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the `PersianTV` folder
4. Wait for Gradle sync to complete

### 2. Build and Run

1. Connect your Android TV device or start an emulator
2. Click "Run" or press Shift+F10
3. Select your target device

## Project Structure

```
PersianTV/
├── app/
│   ├── src/main/
│   │   ├── java/ir/persiantv/
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/
│   │   │   │   ├── Channel.kt
│   │   │   │   ├── ChannelRepository.kt
│   │   │   │   └── PreferencesManager.kt
│   │   │   └── ui/
│   │   │       ├── channel/
│   │   │       │   ├── ChannelListItem.kt
│   │   │       │   └── ChannelListPanel.kt
│   │   │       ├── player/
│   │   │       │   └── PlayerScreen.kt
│   │   │       ├── settings/
│   │   │       │   └── SettingsPanel.kt
│   │   │       ├── viewmodel/
│   │   │       │   └── ChannelViewModel.kt
│   │   │       └── theme/
│   │   │           ├── Color.kt
│   │   │           ├── Theme.kt
│   │   │           └── Type.kt
│   │   ├── assets/
│   │   │   └── channels.json
│   │   └── res/
│   │       ├── drawable/
│   │       ├── values/
│   │       └── mipmap-*/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

## Adding Channels

Edit `app/src/main/assets/channels.json` to add or modify channels:

```json
{
  "channels": [
    {
      "id": 1,
      "title": "Channel Name",
      "url": "https://example.com/stream.m3u8",
      "logo": "https://example.com/logo.png",
      "category": "Category"
    }
  ]
}
```

## Navigation

- **D-pad Up/Down**: Navigate channel list
- **D-pad Left/Right**: Switch between panels
- **Enter/Select**: Play channel
- **Back**: Return to channel list

## License

This project is open source and available for personal use.
