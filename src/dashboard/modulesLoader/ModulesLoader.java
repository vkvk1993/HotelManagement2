package dashboard.modulesLoader;

import java.io.IOException;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import dashboard.DashboardCommonTools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class ModulesLoader {

  @FXML
  private TabPane userMainTabPane;
  @FXML
  private TabPane balanceSheetMainTabPane;



  @FXML
  public void initialize() {
    DashboardCommonTools.populateUserTabs(userMainTabPane, HMConstants.HM_MAIN_USER_TAB);
    DashboardCommonTools.populateUserTabs(balanceSheetMainTabPane,
        HMConstants.HM_MAIN_DATA_SHEET_TAB);
  }

  @FXML
  public void logout() {
    try {
      VBox root = (VBox) FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
      ServerObjectImpl.getInstance().getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
