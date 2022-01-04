# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.3.0] - 2022-01-05

### Added

- GAME: Basic game implementation
    - game structure in docs/game/ (currently in version 2)
    - creating a game with given dimension is possible and contains the needed objects like player,
      start, finish and ground objects
    - Test main class for checking right behaviour of ingame objects
    - __IMPORTANT:__ Moving the player has __NOT__ been tested yet and may not be working. For this
      reason it hasn't been included in the test main class and won't be called

### Changed

- all `build.gradle`s in the modules have been updated to unify their overall structure

### Fixed

- Updated SpotBugs because of deprecated Security features used by SpotBugs in Java 17 (they will be
  removed in Java 18)

## [0.2.0] - 2022-01-03

### Added

- Editor window GUI:
    - FXML file for construction of the view
      with [SceneBuilder](https://gluonhq.com/products/scene-builder/)
    - controller class to initialize the actual window
    - helper and utility classes for data structuring


- Playing field window GUI:
    - Also using SceneBuilder and FXML
    - controller class to initialize the actual window
    - helper and utility classes for data structuring


- Mockup game to simulate the looks on the GUI and for testing purposes (will be deprecated and
  removed later on)

### Changed

- `gui/build.gradle` for updates on the module (like integration of code style tests)

## [0.1.1] - 2021-12-21

### Changed

- Update on the project sketch in docs

## [0.1.0] - 2021-12-20

### Added

- modules `client, communication, game, gui, objects, server`
- dummy classes `owlngo.client.Client` and `owlngo.server.Server` to test the gradle build process
- build.gradle files for those modules with some basic features like CheckStyle, SpotBugs, Spotless
  and JavaFX

### Changed

- Different project structure based of the project sketch

## [0.0.4] - 2021-12-20

### Added

- sketch for project structure (beta)

### Changed

- README.md: checks on the first tasks

## [0.0.3] - 2021-12-19

### Added

- sketches for the editor window in docs/

## [0.0.2] - 2021-12-18

### Added

- CHANGELOG.md for overview of changes through the project workflow
- docs/ directory at root for non-code files like diagrams and sketches
- .gitlab-ci.yml file for Continuous Integration (CI) pipelines at GitLab
- gradle wrapper for initialization as a gradle project
- .gitignore file for unneeded files on the repository

## [0.0.1] - 2021-12-18

### Added

- CHANGELOG.md added
- Add .gitlab-ci.yml
- Set up gradle project
- Add .gitignore

### Changed

### Fixed

### Removed

[Unreleased]:



