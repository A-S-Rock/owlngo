# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
## [0.6.5] - 2022-01-22
### Added 
- Added editor working together with GameViewScreen and PlayfieldWindow (for testing only)
- Reworked design of editor window



## [0.6.4] - 2022-01-21

### Added
- Reworked GameOverScreen
- Added GameSolvedScreen
- Added GameGivenUpScreen
- Connected playerStatus WIN and LOSE to corresponding Screens

## [0.6.3] - 2022-01-20

### Added
- Added WelcomeScreen
- Added GameViewScreen
- Added HighscoreScreen
- Added LoadLevelScreen
- Added GameOverScreen
- Inserted gradletasks for starting Game and WelcomeScreen seperately

## [0.6.2] - 2022-01-16

### Added
- Added OwnlGo constructor with the parameter "Level level".


## [0.6.1] - Clean-up 2022-01-16

### Deleted
- unused Classes in owlngo.gui > editor deleted
- subdirectory olngo.gui > data deleted
- package objects deleted
- 

## [0.6.0] - 2022-01-16

### Added

- GUI: Enhanced version of the game window which now actually displays the moves!
    - right now, jump means "fly", because falling is switched off
    - animations are not implemented yet (will be done in 0.7.0)
- Readme: short presentation of the project and how to use it.

## [0.5.0] - 2022-01-07

### Added

- GUI: Enhanced version of the game window which now actually displays the game!
    - right now without FXML (could be added)
    - Game window now shows the default level from the actual game (10 rows x 10 columns) with
      start, player and finish
    - movement still not implemented (will be done in 0.6.0)

### Fixed

- level layout was upside down, now it shows properly
- default start, player and finish were on the wrong row

## [0.4.0] - 2022-01-06

### Added

- CLIENT-SERVER: Basic communication implementation
    - Client and Server now can communicate with each other
    - For now there is only sent a test message from the client to the server

### Fixed

- `.gitlab-ci.yml` now caches the right builds from all subprojects

## [0.3.0] - 2022-01-05

### Added

- GAME: Basic game implementation
    - Game structure in docs/game/ (currently in version 2)
    - Creating a game with given dimension is possible and contains the needed objects like player,
      start, finish and ground objects
    - Test main class for checking right behaviour of ingame objects
    - __IMPORTANT:__ Moving the player has __NOT__ been tested yet and may not be working. For this
      reason it hasn't been included in the test main class and won't be called

### Changed

- All `build.gradle`s in the modules have been updated to unify their overall structure
- `game/build.gradle` for using `gradle run` and suppressing warnings concerning properties of
  ingame objects

### Fixed

- Updated SpotLess to 6.1.0 and SpotBugs to 5.0.3 in `settings.gradle` because of deprecated
  Security features used by SpotBugs in Java 17 (they will be removed in Java 18)

## [0.2.0] - 2022-01-03

### Added

- Editor window GUI:
    - FXML file for construction of the view
      with [SceneBuilder](https://gluonhq.com/products/scene-builder/)
    - Controller class to initialize the actual window
    - Helper and utility classes for data structuring


- Playing field window GUI:
    - Also using SceneBuilder and FXML


- Mockup game to simulate the looks on the GUI and for testing purposes (will be deprecated and
  removed later on)

### Changed

- `gui/build.gradle` for updates on the module (like integration of code style tests)

## [0.1.1] - 2021-12-21

### Changed

- Update on the project sketch in docs

## [0.1.0] - 2021-12-20

### Added

- Modules `client, communication, game, gui, objects, server`
- Dummy classes `owlngo.client.Client` and `owlngo.server.Server` to test the gradle build process
- `build.gradle` files for those modules with some basic features like CheckStyle, SpotBugs,
  Spotless and JavaFX

### Changed

- Different project structure based of the project sketch

## [0.0.3] - 2021-12-20

### Added

- Sketch for project structure (beta)

### Changed

- README.md: checks on the first tasks

## [0.0.2] - 2021-12-19

### Added

- Sketches for the editor window in docs/

## [0.0.1] - 2021-12-18

### Added

- CHANGELOG.md for overview of changes through the project workflow
- `docs/` directory at root for non-code files like diagrams and sketches
- `.gitlab-ci.yml` file for Continuous Integration (CI) pipelines at GitLab
- gradle wrapper to build a gradle project
- `.gitignore` file for unneeded files on the repository

[Unreleased]: https://gitlab2.cip.ifi.lmu.de/sosy-lab/peegs-ws-21/level-editor-team-1/-/compare/v0.6.3...main?from_project_id=3507
[0.6.4]: 

[0.6.3]: https://gitlab2.cip.ifi.lmu.de/sosy-lab/peegs-ws-21/level-editor-team-1/-/compare/v0.5.0...v0.6.3?from_project_id=3507

[0.5.0]: https://gitlab2.cip.ifi.lmu.de/sosy-lab/peegs-ws-21/level-editor-team-1/-/compare/v0.4.0...v0.5.0?from_project_id=3507

[0.4.0]: https://gitlab2.cip.ifi.lmu.de/sosy-lab/peegs-ws-21/level-editor-team-1/-/compare/v0.3.0...v0.4.0?from_project_id=3507

[0.3.0]: https://gitlab2.cip.ifi.lmu.de/sosy-lab/peegs-ws-21/level-editor-team-1/-/releases/v0.3.0
