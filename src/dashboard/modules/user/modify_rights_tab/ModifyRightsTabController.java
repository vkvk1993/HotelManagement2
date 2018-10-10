package dashboard.modules.user.modify_rights_tab;

import java.sql.ResultSet;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class ModifyRightsTabController {

  @FXML
  TitledPane userListTitlesPane;
  @FXML
  ListView<Label> usersListTitledPaneListView;
  @FXML
  TitledPane allowedUserModulesTitledPane;
  @FXML
  TitledPane allowedDataSheetModulesTitledPane;
  @FXML
  VBox allowedDataSheetModulesVBox;
  @FXML
  VBox allowedUserModulesVBox;
  @FXML
  Button saveButton;

  private String checkboxValues = "";

  @FXML
  public void initialize() {
    populateUsersList();
    populateDataSheetModulesList();
    populateUserModulesList();
  }

  public void saveButtonClicked() {
    try {
      System.out.println(checkboxValues);
      String updateDataQuery = "Update " + HMConstants.HM_USER_RIGHT_TABLE + " SET "
          + HMConstants.HMUserRights.HMRIGHT_USER_TAB_RIGHT_IDS + " = '" + checkboxValues
          + "' WHERE " + HMConstants.HMUserRights.HMRIGHT_USER_ID + " = '"
          + usersListTitledPaneListView.getSelectionModel().getSelectedItem().getId() + "';";
      ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(updateDataQuery);
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
              String getAllowedRights =
                  "SELECT " + HMConstants.HMUserRights.HMRIGHT_USER_TAB_RIGHT_IDS + " FROM "
                      + HMConstants.HM_USER_RIGHT_TABLE + " WHERE "
                      + HMConstants.HMUserRights.HMRIGHT_USER_ID + " ='" + userId + "';";
              try {
                ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
                    .getColumnDataResultSet(getAllowedRights);
                checkboxValues = rs.getString(1);
                for (Node cb : allowedUserModulesVBox.getChildren()) {
                  if (cb instanceof CheckBox) {
                    CheckBox checkbox = (CheckBox) cb;
                    if (rs.getString(1).contains(checkbox.getId())) {
                      checkbox.setSelected(true);
                    } else {
                      checkbox.setSelected(false);
                    }
                  }
                }
                for (Node cb : allowedDataSheetModulesVBox.getChildren()) {
                  if (cb instanceof CheckBox) {
                    CheckBox checkbox = (CheckBox) cb;
                    if (rs.getString(1).contains(checkbox.getId())) {
                      checkbox.setSelected(true);
                    } else {
                      checkbox.setSelected(false);
                    }
                  }
                }

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
