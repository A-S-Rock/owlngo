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
import owlngo.gui.data.DataManager;
import owlngo.gui.data.LoadLevelRecord;

/** Contoller class for LoadLevelScreen.fxml. */
public class LoadLevelScreenController {

  private static final int LEVEL_NAME_INDEX = 0;
  private static final int AUTHOR_INDEX = 1;

  @FXML Button backToWelcomeScreenButton;
  @FXML Button playSelectedButton;
  @FXML TableView<LoadLevelRecord> tableView;
  @FXML TableColumn<LoadLevelRecord, String> levelNameColumn;
  @FXML TableColumn<LoadLevelRecord, String> authorColumn;

  private final List<LoadLevelRecord> levelRecords = new ArrayList<>();

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

  public LoadLevelScreenController() {
    setupLevelRecords();
    setupTableView();
  }

  private void setupLevelRecords() {
    List<List<String>> receivedLevelNames = DataManager.getInstance().getLevelNamesContent();
    if (receivedLevelNames != null) {
      for (List<String> levelNames : receivedLevelNames) {
        levelRecords.add(
            new LoadLevelRecord(levelNames.get(LEVEL_NAME_INDEX), levelNames.get(AUTHOR_INDEX)));
      }
      DataManager.getInstance().setLevelNamesContent(null);
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
