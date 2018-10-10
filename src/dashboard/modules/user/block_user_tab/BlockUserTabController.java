package dashboard.modules.user.block_user_tab;

import java.sql.ResultSet;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class BlockUserTabController {
  @FXML
  TitledPane usersListTitledPane;
  @FXML
  VBox usersListTitledPaneVBox;

  @FXML
  public void initialize() {
    populateUsersList();

  }

  private void populateUsersList() {
    try {

      String rightsTableSelectQuery =
          "Select * from " + HMConstants.HM_USERS_TABLE + " WHERE " + HMConstants.HMUsers.HMUSERS_ID
              + " != '" + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "';";
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(rightsTableSelectQuery);
      do {
        CheckBox c = new CheckBox(rs.getString(1));
        c.setId(rs.getString(1));
        c.setSelected((rs.getInt(3) == 1) ? true : false);
        c.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent actionEvent) {
            CheckBox c = ((CheckBox) actionEvent.getSource());
            int blockValueToBeUpdated = (c.isSelected() ? 1 : 0);
            String updateUserQuery = "UPDATE " + HMConstants.HM_USERS_TABLE + " SET "
                + HMConstants.HMUsers.HMUSERS_USER_BLOCK + " = " + blockValueToBeUpdated + " WHERE "
                + HMConstants.HMUsers.HMUSERS_ID + " = '" + c.getId() + "';";
            try {
              ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(updateUserQuery);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
        usersListTitledPaneVBox.getChildren().add(c);
      } while (rs.next());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
