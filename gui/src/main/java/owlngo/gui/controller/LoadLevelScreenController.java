package owlngo.gui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import owlngo.communication.Connection;
import owlngo.communication.messages.LoadLevelRequest;
import owlngo.gui.data.CommunicationManager;
import owlngo.gui.data.DataManager;
import owlngo.gui.data.LoadLevelRecord;


/** Contoller class for LoadLevelScreen.fxml. */
public class LoadLevelScreenController {

  private static final int LEVEL_NAME_INDEX = 0;
  private static final int AUTHOR_INDEX = 1;
  private static final String PLAY_BUTTON_NO_LEVEL_SELECTED = "No Level selected!";
  private static final String PLAY_BUTTON_PLAY = "Play!";
  private static final String PLAY_BUTTON_DOWNLOADING = "Downloading...";

  @FXML AnchorPane root;
  @FXML Button backToWelcomeScreenButton;
  @FXML Button playSelectedButton;
  @FXML TableView<LoadLevelRecord> tableView;
  @FXML TableColumn<LoadLevelRecord, String> levelNameColumn;
  @FXML TableColumn<LoadLevelRecord, String> authorColumn;
  @FXML Label selectedLevelLabel;

  private final List<LoadLevelRecord> levelRecords = new ArrayList<>();
  private final CommunicationManager communicationManager;
  private final Connection connection;
  private final StringProperty playSelectedButtonState =
      new SimpleStringProperty(PLAY_BUTTON_NO_LEVEL_SELECTED);
  private final MediaPlayer mediaPlayer;

  @FXML
  void initialize() {
    backToWelcomeScreenButton.setOnAction(
        event -> {
          mediaPlayer.stop();
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
          ControllerUtils.createScene(event, fxmlLoader);
        });
    playSelectedButton.textProperty().bindBidirectional(playSelectedButtonState);
    playSelectedButton.setOnAction(this::playLevelAction);

    tableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue != null) {
                sendLoadLevelRequest(newValue.getLevelName());
                selectedLevelLabel.setText(newValue.getLevelName());
                playSelectedButtonState.set(PLAY_BUTTON_PLAY);
                playSelectedButton.mouseTransparentProperty().set(false);
              }
            }));
  }

  /** Initiates the level screen with a table of stored levels on the server. */
  public LoadLevelScreenController() {
    final Media media =
        new Media(Objects.requireNonNull(getClass().getResource("/music/cinematic-dramatic-11120.mp3")).toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
    communicationManager = CommunicationManager.getInstance();
    connection = communicationManager.getConnection();
    setupLevelRecords();
    setupTableView();
  }

  private void sendLoadLevelRequest(String levelName) {
    connection.write(new LoadLevelRequest(communicationManager.getUsername(), levelName));
  }

  private void playLevelAction(ActionEvent event) {
    final String selectedLevel = selectedLevelLabel.textProperty().getValue();
    if (selectedLevel.equals("None")) {
      throw new AssertionError("Button shouldn't be clickable when no level is selected!");
    } else {
      Platform.runLater(() -> playSelectedButtonState.set(PLAY_BUTTON_DOWNLOADING));
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Platform.runLater(
          () -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          });
    }
  }

  private void setupLevelRecords() {
    List<List<String>> receivedLevelNames = DataManager.getInstance().getLevelNamesContent();
    if (receivedLevelNames != null) {
      for (List<String> levelNames : receivedLevelNames) {
        levelRecords.add(
            new LoadLevelRecord(levelNames.get(LEVEL_NAME_INDEX), levelNames.get(AUTHOR_INDEX)));
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void setupTableView() {
    Platform.runLater(
        () -> {
          ObservableList<LoadLevelRecord> loadableLevels =
              FXCollections.observableArrayList(levelRecords);
          tableView.setItems(loadableLevels);

          levelNameColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelRecords.get(0).levelNameProperty().getName()));
          authorColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelRecords.get(0).authorProperty().getName()));
          tableView.getColumns().setAll(levelNameColumn, authorColumn);
        });
  }
}
