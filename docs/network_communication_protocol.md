# Network communication protocol

For the communication protocol between Client and Server and for the persistent storage of data from
the Server, this project uses JSON. Messages and other storable files should be encoded in JSON
format. Used library for this are moshi and moshi-adapter, ver. 1.13.0
(see [here] for more info).

### Client to Server

1. `ConnectionRequest`: Test message delivering the player to the Server.

2. `LoadLevelNamesRequest`: Message requesting the names of the saved level layouts.

3. `LoadLevelRequest`: Message asking for a level layout from the Server to play on.

4. `SaveLevelRequest`: Message asking the Server to save sent level layout locally.

5. `UpdateLevelStatsRequest`: Requests the Server to update the current level's stats like
   highscores, number of tries etc.

6. `GetLevelStatsRequest`: Calling all level's stats from the Server.

### Server to Client

1. `ConnectedNotification`: Confirmation message for log-in process.

2. `LevelNamesNotification`: Sending the names of saved level layouts to the Client.

3. `SendLevelNotification`: Sending the requested level layout to the Server.

4. `LevelSavedNotification`: Confirmation message if level layout has been saved successfully.

5. `LevelStatsUpdatedNotification`: Confirmation message that level stats have been updated.

6. `LevelStatsNotification`: Returns the saved level stats to the Client

***

## Savefiles JSON protocol

As overview which data is saved on Server side and for resource reasons, JSON is also used for
savefiles structure. As before, moshi ver. 1.13.0 is used here as well.

1. `UsersSavefileEntry`: .txt savefile for users joined on the server (maybe with credentials)

2. `LevelSavefileEntry`: .txt savefile for level objects including game-specific data like level
   name, author etc.

3. `LevelStatsSavefileEntry`: .txt savefile for level stats like highscores etc.

[here]: https://search.maven.org/artifact/com.squareup.moshi/moshi/1.13.0/jar 
