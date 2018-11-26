package login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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

  @FXML
  public void initialize() {
    try {
      String rightsTableSelectQuery = "Select * from " + HMConstants.HM_ACTIVATION_KEY_TABLE + ";";
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(rightsTableSelectQuery);

      if (rs == null) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Software Activation");
        dialog.setHeaderText("Enter Activation Key:");
        dialog.setContentText("Key:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
          boolean accessGranted = false;
          for (String key : HMConstants.ACTIVATION_KEYS) {
            if (key.equals(result.get())) {
              accessGranted = true;
              break;
            }
          }
          if (accessGranted) {
            insertActivationKeyInHMActivationKeyTable(result.get());
          } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, Wrong activation key.");
            alert.showAndWait();
            initialize();
          }

        } else {
          System.exit(0);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void insertActivationKeyInHMActivationKeyTable(String activationKey) throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_ACTIVATION_KEY_TABLE + " ("
        + HMConstants.HMActivationKey.ACTIVATION_KEY_USED + ") " + "values ('" + activationKey
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

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
