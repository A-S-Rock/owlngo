package owlngo.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/** Utility class offers often-used methods for creating scenes, logos, avatars. */
public class ControllerUtils {

  private static final int SCENE_WIDTH = 1200;
  private static final int SCENE_HEIGHT = SCENE_WIDTH * 2 / 3;

  private static final int LOGO_WIDTH = 300;
  private static final int LOGO_HEIGHT = LOGO_WIDTH;

  /**
   * Creates a new scene with a fxml-window.
   *
   * @param event is a click on a connected button
   * @param fxmlLoader is a sceneBuilder designed window
   */
  public static void createScene(ActionEvent event, FXMLLoader fxmlLoader) {
    try {
      Parent root = fxmlLoader.load();
      Stage scene = new Stage();
      scene.setTitle("Owlngo");
      scene.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
      scene.setResizable(true);
      scene.show();
      ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
      System.out.println("IO Exception while loading a fxml-window");
    }
  }

  public Rectangle createLogoPane(String logoType) {
    Image logoImage = null;
    URL logoURL = null;
    switch (logoType) {
      case "win":
        logoURL = getClass().getResource("/images/logo_animated.gif");
      case "lose":
        logoURL = getClass().getResource("/images/logo_animated.gif");
      case "giveup":
        logoURL = getClass().getResource("/images/logo_animated.gif");
      default:
        logoURL = getClass().getResource("/images/logo_animated.gif");
    }
    try {
      logoImage = new Image(Objects.requireNonNull(logoURL).toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle logoElement = new Rectangle(LOGO_WIDTH, LOGO_HEIGHT, Color.TRANSPARENT);
    logoElement.setFill(new ImagePattern(logoImage));
    return logoElement;
  }
}
