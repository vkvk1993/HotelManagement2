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
  public static final String LOAN_ENTRY = "Loan";

  public static final String QUANTITY_UNIT_PACKAGES = "pcs";
  public static final String QUANTITY_UNIT_KG = "kg";
  public static final String QUANTITY_INIT_BOXES = "boxes";

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
  @FXML
  TextField quantityTextField;
  @FXML
  ComboBox<String> quantityUnitComboBox;
  @FXML
  TextField salaryToTextField;

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
      alert.setContentText("Ooops, Someting went wrong.");
      alert.showAndWait();
    }
  }

  @FXML
  public void initialize() {
    paymentModeComboBox.getItems().add("Bank");
    paymentModeComboBox.getItems().add("Cash");
    expenseTypeComboBox.getItems().add(SALARY_EXPENSE);
    expenseTypeComboBox.getItems().add(PURCHASE_ENTRY);
    expenseTypeComboBox.getItems().add(LOAN_ENTRY);
    quantityUnitComboBox.getItems().add(QUANTITY_UNIT_PACKAGES);
    quantityUnitComboBox.getItems().add(QUANTITY_UNIT_KG);
    quantityUnitComboBox.getItems().add(QUANTITY_INIT_BOXES);
    expenseAmountTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(expenseAmountTextField));
    quantityTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(quantityTextField));
    populateCategorylist();
  }

  @FXML
  public void categoryComboBoxonChange() {
    populateItemslist(categoryComboBox.getValue());
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

  private void populateItemslist(String category) {
    itemComboBox.getItems().clear();
    try {
      String categoryListSelectQuery =
          "SELECT " + HMItems.HM_ITEM + " FROM " + HMConstants.HM_ITEMS_TABLE + " WHERE "
              + HMConstants.HMItems.HM_ITEM_CATEGORY + "='" + category + "';";
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
      quantityTextField.setDisable(false);
      quantityUnitComboBox.setDisable(false);
      salaryToTextField.setDisable(true);
    } else if ((expenseTypeComboBox.getSelectionModel().getSelectedItem()
        .equalsIgnoreCase(SALARY_EXPENSE))) {
      salaryToTextField.setDisable(false);
      itemComboBox.setDisable(true);
      categoryComboBox.setDisable(true);
      quantityTextField.setDisable(true);
      quantityUnitComboBox.setDisable(true);
    } else if ((expenseTypeComboBox.getSelectionModel().getSelectedItem()
        .equalsIgnoreCase(LOAN_ENTRY))) {
      itemComboBox.setDisable(true);
      categoryComboBox.setDisable(true);
      quantityTextField.setDisable(true);
      quantityUnitComboBox.setDisable(true);
      salaryToTextField.setDisable(true);
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

    String quantityUnit = (quantityUnitComboBox.getSelectionModel().getSelectedItem() == null)
        ? ""
        : quantityUnitComboBox.getSelectionModel().getSelectedItem();
    String salaryCreditedTo = (salaryToTextField.isDisabled()) ? "" : salaryToTextField.getText();
    String quantityText = (quantityTextField.isDisabled()) ? "" : quantityTextField.getText();
    String amount = expenseAmountTextField.getText();
    if (expenseType != null && expenseMode != null && quantityUnit != null) {
      try {

        String insertDataQuery = "insert into " + HMConstants.HM_EXPENSE_TABLE + " ("
            + HMConstants.HMExpense.HM_EXPENSE_AMOUNT + ", " + HMConstants.HMExpense.HM_CATEGORY
            + ", " + HMConstants.HMExpense.HM_EXPENSE_MODE + ", "
            + HMConstants.HMExpense.HM_EXPENSE_TYPE + ", " + HMConstants.HMExpense.HM_ITEM + ", "
            + HMConstants.HMExpense.HM_USER_ID + "," + HMConstants.HMExpense.HM_EXPENSE_DATE + ","
            + HMConstants.HMExpense.HM_QUANTITY + "," + HMConstants.HMExpense.HM_QUANTITY_UNIT
            + ", " + HMConstants.HMExpense.HM_SALARY_CREDITED_TO + ") " + "values ('"
            + Double.parseDouble(amount) + "','" + category + "','" + expenseMode + "','"
            + expenseType + "','" + item + "','"
            + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "','"
            + CommonToolsClass.getDateYYYYMMDD() + "'," + quantityText + ",'" + quantityUnit + "','"
            + salaryCreditedTo + "');";
        return ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
