# Gametime Mod (1.21.1)

A lightweight, clean utility for Minecraft 1.21.1 that displays your total playtime for each world directly on the Singleplayer selection screen.

## Features
- **Seamless UI:** Displays hours and minutes (e.g., ` 5h 24m`) next to the world name.
- **Visuals:** Uses a semi-transparent Gold font (`0xAAFFAA00`) and 80% scaling to match the native Minecraft aesthetic.
- **Optimized Performance:** Uses "Lazy Loading" to fetch playtime data only when needed, ensuring the menu remains lag-free.
- **Built for 1.21.1:** Updated for the latest 2D matrix rendering system (`Matrix3x2fStack`).

## Technical Details
- **Platform:** Fabric
- **Language:** Java
- **Mixin Target:** `WorldSelectionList.WorldListEntry`

## How to Build
1. Clone the repository.
2. Run `./gradlew build`.
3. Find the `.jar` file in `build/libs`.
