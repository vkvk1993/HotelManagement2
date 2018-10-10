package dashboard.modules.user.logout_tab;

import java.io.IOException;

import ServerObject.ServerObjectImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LogoutTabController {
  @FXML
  Button logoutButton;

  @FXML
  public void initialize() {
    try {
      VBox root = (VBox) FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
      ServerObjectImpl.getInstance().getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void logoutButtonClicked() {
    try {
      VBox root = (VBox) FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
      ServerObjectImpl.getInstance().getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
