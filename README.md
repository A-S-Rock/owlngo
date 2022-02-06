# Owl'n go - a jump 'n run game and editor

This application is a javafx using jump and run game, where the player can select existing levels to
play and create new levels.

We use a server-client-architecture to show and deliver existing levels with their highscores. For
messages between each other we use the moshi (json) protocol.

### Running the game - prerequisites

- This project is written with __Java 17__. It should download and install itself on running if not
  present on the user's maschine. If there are problems, please contact us.

- As this project doesn't create a jar to run, you either will need to use the Terminal for running
  Gradle tasks or an IDE to run it interactively.

### Start the server

__Caution: If the directory and file structure for the server got deleted or corrupted, a server
restart is required to initialize the savefile structure.__

- *In IntelliJ*: Use the gradle task "**owlngo > server > Tasks >
  application > run**".


- *In Terminal*: Use `./gradlew :server:run` to start the server.

### Start the client (must be after the server has started)

- *In IntelliJ*: Use the gradle task "**owlngo > client > Tasks >
  application > run**".


- *In Terminal*: Use `./gradlew :client:run` to connect the client to the server.

### Controls

**F** - switch between walk mode and flight mode.

#### Flight mode off - endurance bar gray - owl falls down

**W** - jump

**A** - step to the left

**D** - step to the right

**Q** - jup to the left

**E** - jup to the right

#### Flight mode on - endurance bar red - owl does not fall down

**W** - fly up

**X** - fly down

**A** - fly to the left

**D** - fly to the right

**Q** - fly to the left and up

**E** - fly to the right and up

**Y** - fly to the left and down

**C** - fly to the right and down


The Editor controls are described in its window.

***

What we have so far (and what's coming next)

- [x] created a simple server-client-architecture (but not used right now)
- [x] created a game model handling the whole game
- [x] created a javafx GUI currently showing just the gameplay screen (without showing a victory
  screen at the end)
- [x] added network communication protocol in docs/ [here](docs/network_communication_protocol.md)
- [ ] added animations to all moves
- [ ] added a timer and some controls to the gameplay screen
- [x] added an editor
- [x] added all previously concepted windows
- [x] connected everything with the server-client-architecture
