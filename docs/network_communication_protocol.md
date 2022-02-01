# Network communication protocol

_Adjusted from Task 3's JSON communication protocol._

For the communication protocol between Client and Server and for the persistent storage of data from
the Server, this project uses JSON. Messages and other storable files should be encoded in JSON
format. Used library for this are moshi and moshi-adapter, ver. 1.13.0
(see [here] for more info).

***

### Level JSON representation

To save Level objects in an appropriate way a special `LevelJSON` class has been introduced.

   ```java 
   {"numCols":<NUM_COLS>,"numRows":<NUM_ROWS>,"objectsInGame":<OBJECTS_IN_GAME>}
   ```

- `int <NUM_COLS>`: Number of columns in the level.
- `int <NUM_ROWS>`: Number of rows in the level.
- `List<ObjectInGameJson> <OBJECTS_IN_GAME>`: JSON representation of all objects present in the
  level.<br><br>

  To represent an object in-game (`ObjectInGameJson`) in JSON:
   ```json lines
   {"objectJsonType":<OBJECT_JSON_TYPE>,"objectType":<OBJECT_TYPE>,"coordinate":<COORDINATE>}
   ```
    - `enum <OBJECT_TYPE>`: Type of object (currently `"NONE"`, `"AIR"`, `"GROUND"`, `"PLAYER"`
      , `"START"`, `"FINISH"`).
    - `CoordinateJSON <COORDINATE>`: JSON representation of a coordinate in the level<br><br>

  If the object is a moveable object like a player, (`ObjectInGameJson`) in JSON is extended:
   ```json lines
   {"objectJsonType":<OBJECT_JSON_TYPE>,"objectType":<OBJECT_TYPE>,"coordinate":<COORDINATE>,
  "possibleMoves":<POSSIBLE_MOVES>}
   ```
    - `List<MoveJson> <POSSIBLE_MOVES>`: JSON representation of moves the moveable object can
      make.<br><br>

  `MoveJson` in JSON:

   ```json lines
   {"moveType": <MOVE_TYPE>,"newCoordinate": <NEW_COORDINATE>}
   ```
    - `enum <MOVE_TYPE>`: Type of move (currently `"LEFT"`, `"RIGHT"`, `"JUMP"`, `"FALL"`).
    - `CoordinateJSON <NEW_COORDINATE>`: JSON representation of the new coordinate after the
      move.<br><br>

  `CoordinateJson` in JSON:

   ```json lines
   {"row": <ROW>,"column": <COLUMN>}
   ```
    - `int <ROW>`: row coordinate.
    - `int <COLUMN>`: column coordinate.

***

### Client to Server

1. `ConnectionRequest`: Test message delivering the player to the Server.
   ```json lines
   {"messageType":"ConnectionRequest","playerName":<PLAYER_NAME>}
   ```
    - `String <PLAYER_NAME>`: the client's (player's) name <br><br>

2. `LoadLevelInfosRequest`: Message requesting info of the saved level layouts.
   ```json lines
   {"messageType":"LoadLevelInfosRequest","playerName":<PLAYER_NAME>}
   ```
    - `String <PLAYER_NAME>`: the client's (player's) name<br><br>

3. `LoadLevelRequest`: Message asking for a level layout from the Server to play on.
   ```json lines
   {"messageType":"LoadLevelRequest","levelName":<LEVEL_NAME>,"playerName":<PLAYER_NAME>}
   ```
    - `String <LEVEL_NAME>`: the name of the saved level.
    - `String <PLAYER_NAME>`: the client's (player's) name.<br><br>

4. `SaveLevelRequest`: Message asking the Server to save sent level layout locally.
   ```json lines
   {"messageType":"SaveLevelRequest","author":<AUTHOR>,"level":<LEVEL>,"levelName":<LEVEL_NAME>}
   ```
    - `String <AUTHOR>`: the level creator's name
    - `LevelJSON <LEVEL>`: JSON representation of the level object to be saved (see previous
      description of `LevelJSON`)
    - `String <LEVEL_NAME>`: the name given to the level<br><br>

#### Upcoming

5. `UpdateLevelStatsRequest`: Requests the Server to update the current level's stats like
   highscores, number of tries etc.

6. `GetLevelStatsRequest`: Calling all level's stats from the Server.

***

### Server to Client

1. `ConnectedNotification`: Confirmation message for log-in process.
   ```json lines
   {"messageType":"ConnectedNotification","playerName":<NAME>}
   ```
    - `String <NAME>`: the client's (player's) name <br><br>

2. `LevelInfosNotification`: Sending the names and authors of saved level layouts to the Client.
   ```json lines
   {"messageType":"LevelInfosNotification","levelInfos":<LEVEL_INFOS>}
   ```
    - `ListJSON<ListJSON<String>> <LEVEL_INFOS>`: JSON representation of all saved level's
      infos.<br><br>

3. `SendLevelNotification`: Sending the requested level layout to the Server.
   ```json lines
   {"messageType":"SendLevelNotification","levelName":<LEVEL_NAME>,"level":<LEVEL>}
   ```
    - `String <LEVEL_NAME>`: Loaded level's name.
    - `LevelJSON <LEVEL>`: Loaded level's layout (see previous description of `LevelJSON`).<br><br>

4. `LevelSavedNotification`: Confirmation message if level layout has been saved successfully.
   ```json lines
   {"messageType":"LevelSavedNotification","levelName":<LEVEL_NAME>}
   ```
    - `String <LEVEL_NAME>`: Loaded level's name.

#### Upcoming

5. `LevelStatsUpdatedNotification`: Confirmation message that level stats have been updated.

6. `LevelStatsNotification`: Returns the saved level stats to the Client

***

## Savefiles JSON protocol

As overview which data is saved on Server side and for resource reasons, JSON is also used for
savefiles structure. As before, moshi ver. 1.13.0 is used here as well.

1. `LevelSavefile`: For every level layout a <LEVEL_NAME>.txt savefile is generated including
   game-specific data like level name, author etc.
   ```json lines
   {"dataType":"LevelSavefile","levelName":<LEVEL_NAME>,"author":<AUTHOR>,"level":<LEVEL>}
   ```
    - `String <LEVEL_NAME>`: Saved level's name (also file name).
    - `String <AUTHOR>`: Creator of the saved level.
    - `LevelJSON <LEVEL>`: Level's layout (see previous description of `LevelJSON`) consisting of
      background elements (air) and interaction elements (ground, player, start, finish etc.).

#### Upcoming

2. `UsersSavefileEntry`: .txt savefile for users joined on the server (maybe with credentials)

3. `LevelStatsSavefileEntry`: .txt savefile for level stats like highscores etc.

[here]: https://search.maven.org/artifact/com.squareup.moshi/moshi/1.13.0/jar 
