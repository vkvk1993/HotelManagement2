package dashboard.modules.data_sheet.add_item_tab;

import java.sql.ResultSet;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import constants.HMConstants.HMCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;

public class AddItemTabController {

  @FXML
  TitledPane categoryListTitlesPane;
  @FXML
  ListView<Label> categoryListTitledPaneListView;
  @FXML
  TextField itemNameTextField;
  @FXML
  Button addItemButton;

  @FXML
  public void initialize() {
    populateCategorylist();
  }

  private boolean insertNewItem() {
    try {
      String insertDataQuery =
          "insert into " + HMConstants.HM_ITEMS_TABLE + " (" + HMConstants.HMItems.HM_USER_ID + ", "
              + HMConstants.HMItems.HM_ITEM_CATEGORY + ", " + HMConstants.HMItems.HM_ITEM + ") "
              + "values ('" + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "','"
              + categoryListTitledPaneListView.getSelectionModel().getSelectedItem().getId() + "','"
              + itemNameTextField.getText() + "');";
      return ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  private void populateCategorylist() {
    try {
      String categoryListSelectQuery =
          "SELECT " + HMCategory.HM_CATEGORY + " FROM " + HMConstants.HM_CATEGORY_TABLE;
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(categoryListSelectQuery);
      if (rs != null) {
        do {
          Label ll = new Label(rs.getString(1));
          ll.setId(rs.getString(1));
          categoryListTitledPaneListView.getItems().add(ll);
        } while (rs.next());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void addItemButtonClicked() {
    if (!itemNameTextField.getText().isEmpty()
        && categoryListTitledPaneListView.getSelectionModel().getSelectedItem() != null) {
      if (insertNewItem()) {
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
  }
}
