# Blocktopograph

## How this work in future?

### Display worlds
1. Use shizuku to access com.mojang data first
2. Copy data to other directory
3. Read the world data
4. Display on app

*Note: apply data back to original directory after stop the app*

### Edit world data
#### level.dat
- Can be edit directly in the app
#### world data (chunks, entities, biomes,...)
1. Open a server from app and go to minecraft to edit directly (support resource packs)
2. After edited, if you exit server, that will save data and apply to original

## Build

Clone project in Android Studio: `File -> New -> Project from Version Control -> Git`
Install missing SDK components. Android Studio would give you the auto-fix options.

Or Manual

```shell
git clone https://github.com/NguyenDuck/blocktopograph.git --recursive
cd blocktopograph
./gradlew build assembly
```

## Contributing

Always welcome! Fork the project, improve and publish!