# WoofOn

WoofOn is a small Android app for waking up computers on your local network with
**Wake-on-LAN (WoL)**. It lets you store the devices you care about and send a
magic packet to any of them with a single tap.

## About

Wake-on-LAN works by broadcasting a special "magic packet" over the network.
The target machine's network card listens for this packet, even while the
computer is powered off, and boots the system when the packet contains the
machine's MAC address.

## Requirements

- Android 8.1 (API 27) or newer.
- The phone and the target computer must be on the same local network.
- Wake-on-LAN must be enabled in the target computer's BIOS/UEFI and network
  adapter settings.

## How to use

### Add a device

1. Tap the **+** floating action button on the home screen.
2. Fill in the dialog:
   - **Device Name** – any label you like, e.g. `Office PC`.
   - **MAC Address** – the target machine's MAC, e.g. `AA:BB:CC:DD:EE:FF`
     (the field formats it for you as you type).
   - **Broadcast Address** – the broadcast address of your network, e.g.
     `192.168.1.255`.
   - **Port** – usually `9` (the app defaults to `9`; `7` is also offered).
3. Tap **Add**. The device appears as a card on the home screen.

Empty fields are highlighted with an error icon until they are filled in.

### Wake a device

- **Tap a card** to send a magic packet to that device. The computer should
  power on shortly after.

### Select devices

- **Long-press a card** to enter selection mode. You can select several cards.

## Building and running

This is a standard Gradle Android project.

```bash
# Build the debug APK
./gradlew assembleDebug

# Compile only the Kotlin sources (quick check)
./gradlew :app:compileDebugKotlin

# Install on a connected device or emulator
./gradlew installDebug
```

You can also open the project in Android Studio and run the `app`
configuration.

The debug APK is written to `app/build/outputs/apk/debug/`.

## Project structure

```
app/src/main/java/com/example/woofon/
├── MainActivity.kt              # Entry point, applies theme and shows HomePage
├── HomePage.kt                  # Home screen: device list, FAB, selection actions
├── SettingsPage.kt              # Settings screen (SSH placeholder)
├── SSHPage.kt                   # Placeholder SSH screen
├── components/
│   ├── AddDeviceDialog.kt       # Add / edit device dialog
│   ├── DeviceCard.kt            # One device card
│   ├── WoLField.kt              # Text field with MAC formatting
│   └── SSHCard.kt               # Settings card placeholder
├── data/viewmodels/
│   ├── HomeViewModel.kt         # Home screen state and device operations
│   ├── MainViewModel.kt         # Theme state
│   ├── DeviceDao.kt             # Room DAO (read/insert/update/delete)
│   ├── DeviceDB.kt              # Room database instance
│   └── models/DeviceModel.kt    # Device entity
├── network/
│   └── WakeOnLAN.kt             # Magic packet construction and UDP send
└── ui/theme/                    # Compose theme (colors, typography)

app/src/main/res/                # Resources, launcher icon, strings
```

## Tech stack

- Kotlin + Jetpack Compose (Material 3)
- Android ViewModel + Kotlin StateFlow
- Room (KSP) for local storage
- AndroidX Lifecycle, Activity Compose

## Notes and limitations

- The app sends a single magic packet; some machines may need it sent a few
  times before they wake.
- Wake-on-LAN only works within the local network (or over a correctly
  configured VPN); it will not work over the internet by default.
- SSH functionality is not implemented yet.
