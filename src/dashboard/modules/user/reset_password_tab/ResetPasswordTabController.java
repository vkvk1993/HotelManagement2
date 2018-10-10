package dashboard.modules.user.reset_password_tab;

import java.sql.ResultSet;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class ResetPasswordTabController {

  @FXML
  TitledPane userListTitlesPane;
  @FXML
  ListView<Label> usersListTitledPaneListView;
  @FXML
  Button resetPasswordButton;
  @FXML
  TextField currentPasswordTextField;
  @FXML
  TextField newPasswordTextField;
  @FXML
  TextField confirmPasswordTextField;

  @FXML
  public void initialize() {
    populateUsersList();

  }

  public void resetPasswordButtonClicked() {
    String newPass = newPasswordTextField.getText();
    String confirmPass = confirmPasswordTextField.getText();
    if (newPass.equals(confirmPass)) {
      System.out.println(newPasswordTextField.getText());
      System.out.println(confirmPasswordTextField.getText());
      try {
        String userId = usersListTitledPaneListView.getSelectionModel().getSelectedItem().getId();
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

  private void populateUsersList() {
    try {

      String usersTableSelectQuery =
          "Select " + HMConstants.HMUsers.HMUSERS_ID + " from " + HMConstants.HM_USERS_TABLE;
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(usersTableSelectQuery);
      do {
        Label lable = new Label(rs.getString(1));
        lable.setId(rs.getString(1));
        usersListTitledPaneListView.getItems().add(lable);
      } while (rs.next());
      usersListTitledPaneListView.getSelectionModel().getSelectedItems()
          .addListener(new ListChangeListener<Label>() {
            @Override
            public void onChanged(Change<? extends Label> c) {
              String userId =
                  usersListTitledPaneListView.getSelectionModel().getSelectedItem().getId();
              String usersTablePasswordSelectQuery = "Select " + HMConstants.HMUsers.HMUSERS_PASSWORD
                  + " from " + HMConstants.HM_USERS_TABLE + " WHERE " + HMConstants.HMUsers.HMUSERS_ID
                  + " = '" + userId + "';";
              try {
                ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
                    .getColumnDataResultSet(usersTablePasswordSelectQuery);
                currentPasswordTextField.setText(rs.getString(1));
              } catch (Exception e) {
                e.printStackTrace();
              }

            }

          });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
