package dashboard.modules.data_sheet.income_tab;

import java.text.DecimalFormat;

import ServerObject.ServerObjectImpl;
import common.CommonToolsClass;
import constants.HMConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class IncomeTabController {

  public static final String LOAN = "Loan";
  public static final String ROOMS = "Rooms";


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
  TextField interestPrecentTextField;
  @FXML
  TextField commissionTextField;

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
    interestPrecentTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(interestPrecentTextField));
    commissionTextField.textProperty()
        .addListener(CommonToolsClass.getNumericFieldListener(commissionTextField));
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
    String gstPercent = gstPrecentTextField.getText();
    String finalAmout = finalAmountTextField.getText();
    String incomeMode = modeComboBox.getSelectionModel().getSelectedItem();
    String loanFrom = loanTakenFromTextField.getText();
    String enteredAmount = amountTextField.getText();
    String interestPercent =
        (interestPrecentTextField.isDisabled()) ? "0" : interestPrecentTextField.getText();
    String commission = (commissionTextField.isDisabled()) ? "0" : commissionTextField.getText();
    if (!finalAmout.isEmpty()) {
      try {

        String insertDataQuery = "insert into " + HMConstants.HM_INCOME_TABLE + " ("
            + HMConstants.HMIncome.HM_INCOME_AMOUNT + ", " + HMConstants.HMIncome.HM_GST_PRECENTAGE
            + ", " + HMConstants.HMIncome.HM_INCOME_MODE + ", "
            + HMConstants.HMIncome.HM_INCOME_TYPE + ", " + HMConstants.HMIncome.HM_LOAN_TAKEN_FROM
            + ", " + HMConstants.HMIncome.HM_USER_ID + "," + HMConstants.HMIncome.HM_INCOME_DATE
            + ", " + HMConstants.HMIncome.HM_ENTERED_AMOUNT + ", "
            + HMConstants.HMIncome.HM_INTEREST_PERCENT + ", " + HMConstants.HMIncome.HM_COMMISSION
            + ") " + "values (" + Double.parseDouble(finalAmout) + ","
            + Integer.parseInt(gstPercent) + ",'" + incomeMode + "','" + incomeType + "','"
            + loanFrom + "','" + ServerObjectImpl.getInstance().getLoginModel().getUserId() + "','"
            + CommonToolsClass.getDateYYYYMMDD() + "', " + enteredAmount + ", " + interestPercent
            + "," + commission + ");";
        return ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public void calculationDataEntered() {
    String gstpercent =
        ((gstPrecentTextField.getText().isEmpty()) ? "0.0" : gstPrecentTextField.getText());
    String interestpercent = ((interestPrecentTextField.getText().isEmpty())
        ? "0.0"
        : interestPrecentTextField.getText());
    double enteredGSTPrecent = Double.parseDouble(gstpercent);
    double enteredInterestPercen = Double.parseDouble(interestpercent);
    if (!amountTextField.getText().isEmpty()) {
      double enteredAmount = Double.parseDouble(amountTextField.getText());
      double finalAmountData = 0.0;
      if (gstPrecentTextField.isDisabled()) {
        finalAmountData = enteredAmount + (enteredAmount * (enteredInterestPercen / 100));
      } else {
        finalAmountData = ((enteredAmount * 100) / (100 + enteredGSTPrecent));
      }
      DecimalFormat format = new DecimalFormat("##.00");
      finalAmountTextField.setText(format.format(finalAmountData));
      gstPrecentTextArea.setText("");
      gstPrecentTextArea.appendText("Amount = " + amountTextField.getText());
      if (gstPrecentTextField.isDisabled()) {
        gstPrecentTextArea.appendText("\nINTEREST% = " + interestPrecentTextField.getText());
      } else {
        gstPrecentTextArea.appendText("\nGST% = " + gstPrecentTextField.getText());
      }
      gstPrecentTextArea.appendText("\nTotal = " + finalAmountTextField.getText());
    }
  }

  public void incomeTypeDropdownChanged() {
    loanTakenFromTextField
        .setDisable(!incomeByComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase(LOAN));
    commissionTextField.setDisable(
        !incomeByComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase(ROOMS));
    interestPrecentTextField
        .setDisable(!incomeByComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase(LOAN));
    gstPrecentTextField
        .setDisable(incomeByComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase(LOAN));
  }
}
