package dashboard.modules.data_sheet.expense_tab;

import java.sql.ResultSet;

import ServerObject.ServerObjectImpl;
import common.CommonToolsClass;
import constants.HMConstants;
import constants.HMConstants.HMCategory;
import constants.HMConstants.HMItems;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ExpenseTabController {

  public static final String SALARY_EXPENSE = "Salary Expense";
  public static final String PURCHASE_ENTRY = "Purchase Entry";

  @FXML
  ComboBox<String> paymentModeComboBox;
  @FXML
  Button expenseSaveButton;
  @FXML
  ComboBox<String> expenseTypeComboBox;
  @FXML
  TextField expenseAmountTextField;
  @FXML
  ComboBox<String> itemComboBox;
  @FXML
  ComboBox<String> categoryComboBox;

  public void expenseSaveButtonClicked() {
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

  @FXML
  public void initialize() {
    paymentModeComboBox.getItems().add("Bank");
    paymentModeComboBox.getItems().add("Cash");
    expenseTypeComboBox.getItems().add(SALARY_EXPENSE);
    expenseTypeComboBox.getItems().add(PURCHASE_ENTRY);
    expenseAmountTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(expenseAmountTextField));

    populateCategorylist();
    populateItemslist();

  }

  private void populateCategorylist() {
    try {
      String categoryListSelectQuery =
          "SELECT " + HMCategory.HM_CATEGORY + " FROM " + HMConstants.HM_CATEGORY_TABLE;
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(categoryListSelectQuery);
      if (rs != null) {
        do {
          categoryComboBox.getItems().add(rs.getString(1));
        } while (rs.next());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void populateItemslist() {
    try {
      String categoryListSelectQuery =
          "SELECT " + HMItems.HM_ITEM + " FROM " + HMConstants.HM_ITEMS_TABLE;
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(categoryListSelectQuery);
      if (rs != null) {
        do {
          itemComboBox.getItems().add(rs.getString(1));
        } while (rs.next());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void expenseTypeDropdownChanged() {
    if (expenseTypeComboBox.getSelectionModel().getSelectedItem()
        .equalsIgnoreCase(PURCHASE_ENTRY)) {
      itemComboBox.setDisable(false);
      categoryComboBox.setDisable(false);
    } else {
      itemComboBox.setDisable(true);
      categoryComboBox.setDisable(true);
    }
  }

  private boolean insertNewItem() {
    String expenseType = expenseTypeComboBox.getSelectionModel().getSelectedItem();
    String expenseMode = paymentModeComboBox.getSelectionModel().getSelectedItem();
    String category = (categoryComboBox.getSelectionModel().getSelectedItem() == null)
        ? ""
        : categoryComboBox.getSelectionModel().getSelectedItem();
    String item = (itemComboBox.getSelectionModel().getSelectedItem() == null)
        ? ""
        : itemComboBox.getSelectionModel().getSelectedItem();
    String amount = expenseAmountTextField.getText();
    if (expenseType != null && expenseMode != null) {
      try {

        String insertDataQuery = "insert into " + HMConstants.HM_EXPENSE_TABLE + " ("
            + HMConstants.HMExpense.HM_AMOUNT + ", " + HMConstants.HMExpense.HM_CATEGORY + ", "
            + HMConstants.HMExpense.HM_EXPENSE_MODE + ", " + HMConstants.HMExpense.HM_EXPENSE_TYPE
            + ", " + HMConstants.HMExpense.HM_ITEM + ", " + HMConstants.HMExpense.HM_USER_ID + ") "
            + "values ('" + amount + "','" + category + "','" + expenseMode + "','" + expenseType
            + "','" + item + "','" + ServerObjectImpl.getInstance().getLoginModel().getUserId()
            + "');";
        return ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
