package dashboard.modules.data_sheet.income_tab;

import ServerObject.ServerObjectImpl;
import common.CommonToolsClass;
import constants.HMConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class IncomeTabController {

  public static final String LOAN = "Loan";

  @FXML
  TextField amountTextField;
  @FXML
  Button incomeButton;
  @FXML
  ComboBox<String> incomeByComboBox;
  @FXML
  ComboBox<String> modeComboBox;
  @FXML
  TextField gstPrecentTextField;
  @FXML
  TextArea gstPrecentTextArea;
  @FXML
  TextField finalAmountTextField;
  @FXML
  TextField loanTakenFromTextField;

  @FXML
  public void initialize() {
    incomeByComboBox.getItems().add("Rooms");
    incomeByComboBox.getItems().add("Restaurent");
    incomeByComboBox.getItems().add("Loan");
    modeComboBox.getItems().add("Bank");
    modeComboBox.getItems().add("Cash");
    amountTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(amountTextField));
    gstPrecentTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(gstPrecentTextField));
  }

  public void incomeButtonClicked() {



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

  private boolean insertNewItem() {
    String incomeType = incomeByComboBox.getSelectionModel().getSelectedItem();
    String amount = amountTextField.getText();
    String gstPercent = gstPrecentTextField.getText();
    String finalAmout = finalAmountTextField.getText();
    String incomeMode = incomeByComboBox.getSelectionModel().getSelectedItem();
    String loanFrom = loanTakenFromTextField.getText();
    if (!finalAmout.isEmpty()) {
      try {

        String insertDataQuery = "insert into " + HMConstants.HM_INCOME_TABLE + " ("
            + HMConstants.HMIncome.HM_AMOUNT + ", " + HMConstants.HMIncome.HM_GST_PRECENTAGE + ", "
            + HMConstants.HMIncome.HM_INCOME_MODE + ", " + HMConstants.HMIncome.HM_INCOME_TYPE
            + ", " + HMConstants.HMIncome.HM_LOAN_TAKEN_FROM + ", "
            + HMConstants.HMIncome.HM_USER_ID + ") " + "values ('" + amount + "',"
            + Integer.parseInt(gstPercent) + ",'" + incomeMode + "','" + incomeType + "','"
            + loanFrom + "','" + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "');";
        return ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public void calculationDataEntered() {
    if (!amountTextField.getText().isEmpty() && !gstPrecentTextField.getText().isEmpty()) {
      double enteredAmount = Double.parseDouble(amountTextField.getText());
      double enteredGSTPrecent = Double.parseDouble(gstPrecentTextField.getText());
      double finalAmountData = enteredAmount + enteredGSTPrecent;
      finalAmountTextField.setText(String.valueOf(finalAmountData));
      gstPrecentTextArea.setText("");
      gstPrecentTextArea.appendText("Amount = " + amountTextField.getText());
      gstPrecentTextArea.appendText("\nGST% = " + gstPrecentTextField.getText());
      gstPrecentTextArea.appendText("\nTotal = " + finalAmountTextField.getText());
    }
  }

  public void incomeTypeDropdownChanged() {
    if (incomeByComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase(LOAN)) {
      loanTakenFromTextField.setDisable(false);
    } else {
      loanTakenFromTextField.setDisable(true);
    }
  }
}
