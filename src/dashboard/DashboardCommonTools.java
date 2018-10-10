package dashboard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DashboardCommonTools {

  /**
   * This method will return a tab change listener, using which tabs will be loaded dynamically.
   * 
   * @return ChangeListener<Tab>
   */
  public static ChangeListener<Tab> getTabChangeListener(String tabName) {
    return new ChangeListener<Tab>() {

      @Override
      public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        newValue.setContent(null);
        if (newValue.getContent() == null) {
          try {
            String fxmlFilePath = "";
            fxmlFilePath = "/dashboard/modules/" + tabName + "/" + newValue.getId().toLowerCase()
                + "/" + newValue.getId().toLowerCase() + ".fxml";
            Parent root = (Parent) FXMLLoader.load(this.getClass().getResource(fxmlFilePath));
            newValue.setContent(root);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        } else {
          Parent root = (Parent) newValue.getContent();
          newValue.setContent(root);
        }
      }
    };
  }

  public static void populateUserTabs(TabPane tabPane, String mainTabName) {
    try {
      String selectUserRightsQuery = "select * from " + HMConstants.HM_USER_RIGHT_TABLE + " where "
          + HMConstants.HMUserRights.HMRIGHT_USER_ID + " = '"
          + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "';";
      String userRights =
          ServerObjectImpl.getInstance().getHsqlDBObject().getColumnData(selectUserRightsQuery, 2);
      List<String> userTabsSubTabList = Arrays.asList(userRights.split(","));
      for (String userTabsSubTab : userTabsSubTabList) {

        selectUserRightsQuery = "SELECT " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + " FROM "
            + HMConstants.HM_RIGHTS_TABLE + " WHERE "
            + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + "='" + userTabsSubTab + "';";
        String mainCategory = ServerObjectImpl.getInstance().getHsqlDBObject()
            .getColumnData(selectUserRightsQuery, 1);
        if (mainCategory.equals(mainTabName)) {
          Tab newTab = new Tab(userTabsSubTab);
          selectUserRightsQuery = "select * from " + HMConstants.HM_RIGHTS_TABLE + " where "
              + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + " = '" + userTabsSubTab + "';";
          userRights = ServerObjectImpl.getInstance().getHsqlDBObject()
              .getColumnData(selectUserRightsQuery, 2);
          newTab.setId(userTabsSubTab);
          newTab.setText(userRights);
          tabPane.getTabs().add(newTab);
        }

      }
      tabPane.getSelectionModel().clearSelection();
      tabPane.getSelectionModel().selectedItemProperty()
          .addListener(DashboardCommonTools.getTabChangeListener(mainTabName));
      tabPane.getSelectionModel().selectFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
