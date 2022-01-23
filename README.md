# Owl'n go - a jump 'n run game and editor

This application is a javafx using jump and run game, where the player can select existing levels to
play and create new levels.

We use a server-client-architecture to show and deliver existing levels with their highscores. For
messages between each other we use the moshi (json) protocol.

For testing purposes the game window, welcome window and editor window can be started separately
through dedicated gradle tasks. When the project is done, these tasks are considered to only be used
for testing and will not be called from users (this will be delegated to the client).

### Start the game

- *In IntelliJ*: Use the gradle task "**owlngo > gui > Tasks >
  other > taskForGameScreen**".


- *In Terminal*: Use `./gradlew :gui:taskforGameScreen` to start the game.

### Start the welcome screen

- *In IntelliJ*: Use the gradle task "**owlngo > gui > Tasks >
  other > taskForWelcomeScreen**".


- *In Terminal*: Use `./gradlew :gui:taskForWelcomeScreen` to start the game.

### Start the welcome screen

- *In IntelliJ*: Use the gradle task "**owlngo > gui > Tasks >
  other > taskForEditorScreen**".


- *In Terminal*: Use `./gradlew :gui:taskForEditorScreen` to start the game.

### Controls

**W** - to make a jump (which is currently set to just jump without falling afterwards)

**A** - to make a step to the left including a one-step fall.

**S** - to make a "small" fall (just 1 field down) (maybe we will use this later to make a power
dive)

**D** - to make a step to the right including a one-step fall.

***

What we have so far (and what's coming next)

- [x] created a simple server-client-architecture (but not used right now)
- [x] created a game model handling the whole game
- [x] created a javafx GUI currently showing just the gameplay screen (without showing a victory
  screen at the end)
- [ ] added animations to all moves
- [ ] added a timer and some controls to the gameplay screen
- [x] added an editor
- [ ] added all previously concepted windows
- [ ] connected everything with the server-client-architecture
