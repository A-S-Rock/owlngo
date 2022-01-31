package owlngo.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import owlngo.gui.data.LoadLevelRecord;

/** Contoller class for LoadLevelScreen.fxml. */
public class LoadLevelScreenController {

  @FXML Button backToWelcomeScreenButton;
  @FXML Button playSelectedButton;
  @FXML TableView<LoadLevelRecord> tableView;
  @FXML TableColumn<LoadLevelRecord, String> levelNameColumn;
  @FXML TableColumn<LoadLevelRecord, String> authorColumn;

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
}
