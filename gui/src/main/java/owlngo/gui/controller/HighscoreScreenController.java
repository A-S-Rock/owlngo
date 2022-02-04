package owlngo.gui.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import owlngo.gui.data.DataManager;
import owlngo.gui.data.LevelStatsRecord;

/** Contoller class for HighscoreScreen.fxml. */
public class HighscoreScreenController {

  private static final int LEVEL_NAME_INDEX = 0;
  private static final int TRIES_INDEX = 1;
  private static final int COMPLETIONS_INDEX = 2;
  private static final int BEST_TIME_INDEX = 3;
  private static final int BY_USER_INDEX = 4;

  @FXML AnchorPane root;
  @FXML Button backToWelcomeScreenButton;
  @FXML TableView<LevelStatsRecord> tableView;
  @FXML TableColumn<LevelStatsRecord, String> levelNameColumn;
  @FXML TableColumn<LevelStatsRecord, Integer> triesColumn;
  @FXML TableColumn<LevelStatsRecord, Integer> completionsColumn;
  @FXML TableColumn<LevelStatsRecord, String> bestTimeColumn;
  @FXML TableColumn<LevelStatsRecord, String> byUserColumn;

  private final List<LevelStatsRecord> levelStatsRecords = new ArrayList<>();

  @FXML
  void initialize() {
    backToWelcomeScreenButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });
  }

  public HighscoreScreenController() {
    setupLevelStatsRecords();
    setupTableView();
  }

  private void setupLevelStatsRecords() {
    List<List<String>> receivedLevelStats = DataManager.getInstance().getLevelStatsContent();
    if (receivedLevelStats != null) {
      for (List<String> levelStats : receivedLevelStats) {
        final String levelName = levelStats.get(LEVEL_NAME_INDEX);
        final int tries = Integer.parseInt(levelStats.get(TRIES_INDEX));
        final int completions = Integer.parseInt(levelStats.get(COMPLETIONS_INDEX));
        final String bestTime = levelStats.get(BEST_TIME_INDEX);
        final String byUser = levelStats.get(BY_USER_INDEX);
        levelStatsRecords.add(
            new LevelStatsRecord(levelName, tries, completions, bestTime, byUser));
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void setupTableView() {
    Platform.runLater(
        () -> {
          ObservableList<LevelStatsRecord> loadableStats =
              FXCollections.observableArrayList(levelStatsRecords);
          tableView.setItems(loadableStats);

          levelNameColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelStatsRecords.get(0).levelNameProperty().getName()));
          triesColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelStatsRecords.get(0).triesProperty().getName()));
          completionsColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelStatsRecords.get(0).completionsProperty().getName()));
          bestTimeColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelStatsRecords.get(0).bestTimeProperty().getName()));
          byUserColumn.setCellValueFactory(
              new PropertyValueFactory<>(levelStatsRecords.get(0).byUserProperty().getName()));
          tableView
              .getColumns()
              .setAll(
                  levelNameColumn, triesColumn, completionsColumn, bestTimeColumn, byUserColumn);
        });
  }
}
