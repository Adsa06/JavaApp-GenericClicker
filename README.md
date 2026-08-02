# JavaApp Generic Clicker

This is a simple clicker game built in Java using the Lanterna library for a text-based terminal UI.

## Features

- Simple clicker gameplay
- Uses Lanterna for terminal rendering
- Easy to run and modify

## Requirements

- Java 21 or newer
- Maven 3.9+ (recommended)
- A JDK that includes `jpackage` (JDK 21+ normally includes it)

## Run from source

```bash
mvn clean package
java -jar target/javaapp-genericclicker-1.0.jar
```

### Build

From the project root:

```bash
mvn clean package
```

```bash
jpackage ^
  --input target ^
  --name GenericClicker ^
  --main-jar javaapp-genericclicker-1.0.jar ^
  --main-class io.github.adsa06.Main ^
  --type app-image ^
  --win-console ^
  --dest dist
```

```bash
jpackage --input target --name GenericClicker --main-jar javaapp-genericclicker-1.0.jar --main-class io.github.adsa06.Main --type app-image --win-console --dest dist
```
