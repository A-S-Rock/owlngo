package owlngo.gui.controller;

import java.io.IOException;
import java.net.URL;
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

  private static final URL STANDARD_LOGO =
      ControllerUtils.class.getResource("/images/logo_animated.gif");
  private static final URL LOSE_LOGO =
      ControllerUtils.class.getResource("/images/logo_animated.gif");
  private static final URL GIVEUP_LOGO =
      ControllerUtils.class.getResource("/images/logo_animated.gif");
  private static final URL WIN_LOGO =
      ControllerUtils.class.getResource("/images/logo_animated.gif");

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

  /**
   * Creates a image-filled rectangle used as a logo depending on the game status.
   *
   * @param logoType is a string to diffentiate between win, lose, giveup and standard
   * @return a properly styled rectangle
   */
  public static Rectangle createLogoPane(String logoType) {
    Image logoImage = null;
    URL logoUrl;
    switch (logoType) {
      case "standard":
        logoUrl = STANDARD_LOGO;
        break;
      case "win":
        logoUrl = WIN_LOGO;
        break;
      case "lose":
        logoUrl = LOSE_LOGO;
        break;
      case "giveup":
        logoUrl = GIVEUP_LOGO;
        break;
      default:
        logoUrl = STANDARD_LOGO;
        break;
    }
    try {
      logoImage = new Image(logoUrl.toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle logoElement = new Rectangle(LOGO_WIDTH, LOGO_HEIGHT, Color.TRANSPARENT);
    logoElement.setFill(new ImagePattern(logoImage));
    return logoElement;
  }
}
