package owlngo.gui.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import owlngo.gui.data.LevelStatsRecord;

/** Contoller class for HighscoreScreen.fxml. */
public class HighscoreScreenController {

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
}
