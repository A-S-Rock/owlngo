# Owl'n go - a jump 'n run game and editor

This application is a javafx using jump and run game, where the player can select existing levels to
play and create new levels.

We use a server-client-architecture to show and deliver existing levels with their highscores. For
messages between each other we use the moshi (json) protocol.

At the current stage it's just possible to start and play one existing and really easy level in a
simple, not animated way.

To **start the game** you need to use the gradle task "**owlngo > gui > Tasks >
application > run**".

To **control the owl as the player avatar** you must use the following buttons:

**W** - to make a jump (which is currently set to just jump without falling afterwards)

**A** - to make a step to the left

**S** - to make a "small" fall (just 1 field down) (maybe we will use this later to make a power
dive))

**D** - to make a step to the right

************

What we have so far (and what's coming next)

- [x] created a simple server-client-architecture (but not used right now)
- [x] created a game model handling the whole game
- [x] created a javafx GUI currently showing just the gameplay screen (without showing a victory
  screen at the end)
- [ ] added animations to all moves
- [ ] added a timer and some controls to the gameplay screen
- [ ] added an editor
- [ ] added all previously concepted windows
- [ ] connected everything with the server-client-architecture
