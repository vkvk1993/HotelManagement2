package dashboard.modules.data_sheet.add_category_tab;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCategoryTabController {

  @FXML
  TextField categoryTextField;
  @FXML
  Button addCategoryButton;

  public void addCategoryButtonOnClick() {

    if (insertNewCategory()) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Success Dialog");
      alert.setHeaderText("Done");
      alert.setContentText("Hurrey! Data Inserted Successfully.");
      alert.showAndWait();
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error Dialog");
      alert.setHeaderText("Look, an Error Dialog");
      alert.setContentText("Ooops, I think categoy already exist in our DB.");
      alert.showAndWait();
    }
  }

  private boolean insertNewCategory() {
    try {
      String insertDataQuery = "insert into " + HMConstants.HM_CATEGORY_TABLE + " ("
          + HMConstants.HMCategory.HM_USER_ID + ", " + HMConstants.HMCategory.HM_CATEGORY + ") "
          + "values ('" + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "','"
          + categoryTextField.getText() + "');";
      return ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
