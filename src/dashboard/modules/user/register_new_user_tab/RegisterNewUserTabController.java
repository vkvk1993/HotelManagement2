package dashboard.modules.user.register_new_user_tab;

import java.sql.ResultSet;
import java.sql.SQLException;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import database.ModulesConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import objects.StrStr;

public class RegisterNewUserTabController {

  @FXML
  TextField userIdTextField;
  @FXML
  TextField passwordTextField;
  @FXML
  TextField confirmTextFiled;
  @FXML
  Button registerButton;
  @FXML
  VBox allowedDataSheetModulesVBox;
  @FXML
  VBox allowedUserModulesVBox;
  @FXML
  TitledPane allowedUserModulesTitlePane;
  @FXML
  TitledPane allowedDataSheetModulesTitlePane;
  private String checkboxValues = "";

  @FXML
  public void initialize() {
    populateDataSheetModulesList();
    populateUserModulesList();
  }


  private void populateDataSheetModulesList() {
    try {

      String rightsTableSelectQuery = "Select * from " + HMConstants.HM_RIGHTS_TABLE + " WHERE "
          + HMConstants.HMRights.HMRIGHT_MAIN_TAB + "='" + HMConstants.HM_MAIN_DATA_SHEET_TAB + "'";
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(rightsTableSelectQuery);
      do {
        CheckBox c = new CheckBox(rs.getString(2));
        c.setId(rs.getString(1));
        c.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent actionEvent) {
            CheckBox c = ((CheckBox) actionEvent.getSource());
            if (c.isSelected()) {
              checkboxValues = checkboxValues + c.getId() + ",";
            } else {
              checkboxValues = checkboxValues.replace(c.getId() + ",", "");
            }
          }
        });
        allowedDataSheetModulesVBox.getChildren().add(c);
      } while (rs.next());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void populateUserModulesList() {
    try {

      String rightsTableSelectQuery = "Select * from " + HMConstants.HM_RIGHTS_TABLE + " WHERE "
          + HMConstants.HMRights.HMRIGHT_MAIN_TAB + "='" + HMConstants.HM_MAIN_USER_TAB + "'";
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(rightsTableSelectQuery);
      do {
        CheckBox c = new CheckBox(rs.getString(2));
        c.setId(rs.getString(1));
        c.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent actionEvent) {
            CheckBox c = ((CheckBox) actionEvent.getSource());
            if (c.isSelected()) {
              checkboxValues = checkboxValues + c.getId() + ",";
            } else {
              checkboxValues = checkboxValues.replace(c.getId() + ",", "");
            }
          }
        });
        allowedUserModulesVBox.getChildren().add(c);
      } while (rs.next());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void registerButtonClicked() {
    if (!passwordTextField.getText().equals(confirmTextFiled.getText())) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error Dialog");
      alert.setHeaderText("Look, an Error Dialog");
      alert.setContentText("Ooops, Password did not match.");
      alert.showAndWait();
    }
    String userId = userIdTextField.getText();
    String password = passwordTextField.getText();
    try {
      insertNormalUserInHMUsersTable(userId, password);
      insertDefaultAdminUserRightsInHMUserRightsTable(userId);
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Done");
      alert.setHeaderText("Succesfully Updated");
      alert.setContentText("We updated our database witht he data provided. Thank you.");
      alert.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  private void insertNormalUserInHMUsersTable(String userId, String password) throws SQLException {
    String insertDataQuery =
        "insert into " + HMConstants.HM_USERS_TABLE + " (" + HMConstants.HMUsers.HMUSERS_ID + ", "
            + HMConstants.HMUsers.HMUSERS_PASSWORD + ", " + HMConstants.HMUsers.HMUSERS_USER_BLOCK
            + ") " + "values ('" + userId + "', '" + password + "',0);";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private void insertDefaultAdminUserRightsInHMUserRightsTable(String userId) throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_USER_RIGHT_TABLE + " ("
        + HMConstants.HMUserRights.HMRIGHT_USER_ID + ", "
        + HMConstants.HMUserRights.HMRIGHT_USER_TAB_RIGHT_IDS + ") " + "values ('" + userId + "', '"
        + checkboxValues + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }
}
