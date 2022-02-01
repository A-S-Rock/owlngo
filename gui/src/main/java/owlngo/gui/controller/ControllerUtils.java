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

  @SuppressWarnings("SuspiciousNameCombination")
  private static final int LOGO_HEIGHT = LOGO_WIDTH;

  private static final URL STANDARD_LOGO =
      ControllerUtils.class.getResource("/images/logo_animated.gif");
  private static final URL LOSE_LOGO = ControllerUtils.class.getResource("/images/logo_lose.gif");
  private static final URL GIVEUP_LOGO =
      ControllerUtils.class.getResource("/images/logo_giveup.gif");
  private static final URL WIN_LOGO = ControllerUtils.class.getResource("/images/logo_win.gif");

  /**
   * Creates a new scene with a fxml-window.
   *
   * @param event is a click on a connected button
   * @param fxmlLoader is a sceneBuilder designed window
   */
  public static void createScene(ActionEvent event, FXMLLoader fxmlLoader) {
    try {
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Owlngo");
      Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
      stage.setScene(scene);
      stage.setResizable(true);
      stage.show();
      if (event == null) {
        return;
      }
      ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
      System.out.println("IO Exception while loading a fxml-window");
      e.printStackTrace();
    }
  }

  /**
   * Creates an image-filled rectangle used as a logo depending on the game status.
   *
   * @param logoType is a string to diffentiate between win, lose, giveup and standard
   * @return a properly styled rectangle
   */
  public static Rectangle createLogoPane(String logoType) {
    Image logoImage = null;
    URL logoUrl;
    if ("win".equals(logoType)) {
      logoUrl = WIN_LOGO;
    } else if ("lose".equals(logoType)) {
      logoUrl = LOSE_LOGO;
    } else if ("giveup".equals(logoType)) {
      logoUrl = GIVEUP_LOGO;
    } else {
      logoUrl = STANDARD_LOGO;
    }
    try {
      logoImage = new Image(Objects.requireNonNull(logoUrl).toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle logoElement = new Rectangle(LOGO_WIDTH, LOGO_HEIGHT, Color.TRANSPARENT);
    logoElement.setFill(new ImagePattern(Objects.requireNonNull(logoImage)));
    return logoElement;
  }
}
