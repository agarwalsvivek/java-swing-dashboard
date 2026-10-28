# Java Swing Dashboard

A small desktop dashboard built with Java Swing, written as a learning project. The plan is to grow it into a live stock ticker.

## What it does

The app opens a single window with three parts:

- **Header**: a "Hello, Dashboard" title.
- **Cards**:
  - **Status**: shows "Running".
  - **Java version**: the version of the JVM the app is running on.
  - **Clicks**: counts how many times you press the button.
- **Footer**: a **Click me** button, and a clock that updates every second.

The window uses the Nimbus look and feel when it's available, and the system default otherwise.

## Requirements

- JDK 21 or newer
- A graphical desktop. On WSL2 this means WSLg, which Windows 11 includes.

You don't need to install Gradle. The included wrapper (`gradlew`) downloads it the first time you build.

## Running

```bash
./gradlew run
```

On Windows (outside WSL):

```bat
gradlew.bat run
```

To build without running:

```bash
./gradlew build
```

## Project structure

```
.
├── build.gradle.kts          # Gradle build (application plugin, Java 21)
├── settings.gradle.kts
├── gradlew / gradlew.bat     # Gradle wrapper
└── src/main/java/com/example/dashboard/
    └── App.java              # Entry point and all UI code
```

## Swing concepts used

- **Event Dispatch Thread (EDT)**: all UI is created via `SwingUtilities.invokeLater`, because Swing is not thread-safe.
- **Layout managers**: `BorderLayout` arranges the header, cards and footer. `GridLayout` places the three cards side by side.
- **Event listeners**: the button's `ActionListener` updates the click counter.
- **`javax.swing.Timer`**: drives the clock. Its callbacks run on the EDT, so they can update the UI safely. The future stock ticker will use the same pattern.
- **Look and feel**: Nimbus is selected through `UIManager`.

## Roadmap

- [ ] Live stock ticker cards
- [ ] Charts for price history
- [ ] Split `App.java` into separate components
