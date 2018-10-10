package login;

import ServerObject.ServerObjectImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LoginController {
  @FXML
  TextField userNameTextField;
  @FXML
  TextField passwordTextField;
  @FXML
  Button loginButton;
  @FXML
  AnchorPane loginAndchorPane;

  public void loginButtonClicked() {
    String userName = userNameTextField.getText();
    String password = passwordTextField.getText();
    try {
      boolean authenticated = ServerObjectImpl.getInstance().authenticateUser(userName, password);
      if (authenticated) {
        VBox root = (VBox) FXMLLoader
            .load(getClass().getResource("/dashboard/modulesLoader/modulesLoader.fxml"));
        ServerObjectImpl.getInstance().getPrimaryStage().getScene().setRoot(root);
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("Ooops, User name and/or password is not present on our floks.");
        alert.showAndWait();
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

}
