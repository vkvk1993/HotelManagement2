package dashboard.modules.user.reset_my_password_tab;

import java.sql.ResultSet;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ResetMyPasswordTabController {
  @FXML
  Button resetPasswordButton;
  @FXML
  TextField newPasswordTextField;
  @FXML
  TextField confirmPasswordTextField;

  public void resetPasswordButtonClicked() {
    String newPass = newPasswordTextField.getText();
    String confirmPass = confirmPasswordTextField.getText();
    if (newPass.equals(confirmPass)) {
      System.out.println(newPasswordTextField.getText());
      System.out.println(confirmPasswordTextField.getText());
      try {
        String userId = ServerObjectImpl.getInstance().getLoginModel().getUserId();
        String usersTableSelectQuery = "Update " + HMConstants.HM_USERS_TABLE + " SET "
            + HMConstants.HMUsers.HMUSERS_PASSWORD + " = '" + newPass + "' WHERE "
            + HMConstants.HMUsers.HMUSERS_ID + " = '" + userId + "';";
        if (ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(usersTableSelectQuery)) {
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Success Dialog");
          alert.setHeaderText("Hurrey !");
          alert.setContentText("Your Password Is Updated.");
          alert.showAndWait();
        } else {
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Error Dialog");
          alert.setHeaderText("Look, an Error Dialog");
          alert.setContentText("Ooops, Someting went wrong. Please check logs.");
          alert.showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error Dialog");
      alert.setHeaderText("Look, an Error Dialog");
      alert.setContentText("Ooops, Both passwords Should match.");
      alert.showAndWait();
    }
  }
}
