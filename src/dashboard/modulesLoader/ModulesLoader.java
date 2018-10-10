package dashboard.modulesLoader;

import constants.HMConstants;
import dashboard.DashboardCommonTools;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

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
  
}
