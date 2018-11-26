package dashboard.modules.data_sheet.report_tab;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import ServerObject.ServerObjectImpl;
import common.CommonToolsClass;
import constants.HMConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import sun.security.action.GetLongAction;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ReportTabController {
  @FXML
  DatePicker fromDatePicker;
  @FXML
  DatePicker toDatePicker;
  @FXML
  TableView<ReportTable> reportTable = new TableView<ReportTable>();
  @FXML
  TableColumn<ReportTable, String> reportTableDateColumn;
  @FXML
  TableColumn<ReportTable, String> reportTableExpenseColumn;
  @FXML
  TableColumn<ReportTable, String> reportTableIncomColumn;
  @FXML
  TableColumn<ReportTable, String> reportTableBalanceForwordColumn;
  @FXML
  TableColumn<ReportTable, String> reportTableBalanceColumn;
  @FXML
  TableColumn<ReportTable, String> reportTableExpenseModeColumn;
  @FXML
  TableColumn<ReportTable, String> reportTableIncomModeColumn;
  @FXML
  Button generateButton;

  private ObservableList<ReportTable> data;

  public static void main(String[] args) {
    // implemented to fetch list of items from xls file.
    // xlsx file to be saved as 1999 to 2003 excel.
    try {
      String filename = "E:" + File.separator + "b.xls";
      FileInputStream fis;
      fis = new FileInputStream(new File(filename));
      HSSFWorkbook workbook = new HSSFWorkbook(fis);
      HSSFSheet sheet = workbook.getSheetAt(0);
      for (int i = 2; i < sheet.getLastRowNum() - 5; i++) {
        Row row = sheet.getRow(i);
        String s = "insert into HMITEMS (userId, itemCategory, item) values ('a','GROCERY RAI','";
        String e = "');";
        System.out.println(s + row.getCell(1).getStringCellValue() + e);
        System.out.println(s + row.getCell(6).getStringCellValue() + e);
        System.out.println(s + row.getCell(11).getStringCellValue() + e);
        System.out.println(s + row.getCell(16).getStringCellValue() + e);

      }
      sheet = workbook.getSheetAt(1);
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        String s = "insert into HMITEMS (userId, itemCategory, item) values ('a','Liquor','";
        String e = "');";
        Row row = sheet.getRow(i);
        System.out.println(s + row.getCell(1).getStringCellValue() + e);

      }
      sheet = workbook.getSheetAt(2);
      for (int i = 3; i <= sheet.getLastRowNum() - 3; i++) {
        Row row = sheet.getRow(i);
        String s = "insert into HMITEMS (userId, itemCategory, item) values ('a','VEGETABLE RAI','";
        String e = "');";
        System.out.println(s + row.getCell(1).getStringCellValue() + e);
        if (!row.getCell(6).getStringCellValue().isEmpty())
          System.out.println(s + row.getCell(6).getStringCellValue() + e);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void fullReportButtonCLicked() {
    ObservableList<ReportTable> allData = FXCollections.observableArrayList(getReportTableList());
    if (allData != null) {

      try {
        String filename = "HotelManagementAllReport_" + CommonToolsClass.getDateYYYYMMDD() + ".xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("Date");
        rowhead.createCell(1).setCellValue("ExpenseUserId");
        rowhead.createCell(2).setCellValue("ExpenseType");
        rowhead.createCell(3).setCellValue("ExpenseMode");
        rowhead.createCell(4).setCellValue("Category");
        rowhead.createCell(5).setCellValue("ExpenseAmount");
        rowhead.createCell(6).setCellValue("Quantity");
        rowhead.createCell(7).setCellValue("QuantityUnit");
        rowhead.createCell(8).setCellValue("IncomeUserId");
        rowhead.createCell(9).setCellValue("IncomeType");
        rowhead.createCell(10).setCellValue("GSTPercentage");
        rowhead.createCell(11).setCellValue("IncomeAmount");
        rowhead.createCell(12).setCellValue("IncomeMode");
        rowhead.createCell(13).setCellValue("LoanTakenFrom");

        rowhead.createCell(14).setCellValue("SalaryCreditTo");
        rowhead.createCell(15).setCellValue("EnteredAmount");
        rowhead.createCell(16).setCellValue("InteresePercentage");
        rowhead.createCell(17).setCellValue("Commission");



        for (int i = 0; i < allData.size(); i++) {
          ReportTable r = allData.get(i);
          HSSFRow row = sheet.createRow((short) i + 1);
          row.createCell(0).setCellValue(r.getDate());
          row.createCell(1).setCellValue(r.getExpenseUserId());
          row.createCell(2).setCellValue(r.getExpenseType());
          row.createCell(3).setCellValue(r.getExpenseMode());
          row.createCell(4).setCellValue(r.getCategory());
          row.createCell(5).setCellValue(r.getExpenseAmount());
          row.createCell(6).setCellValue(r.getQuantity());
          row.createCell(7).setCellValue(r.getQuantityUnit());
          row.createCell(8).setCellValue(r.getIncomeUserId());
          row.createCell(9).setCellValue(r.getIncomeType());
          row.createCell(10).setCellValue(r.getGstPercentage());
          row.createCell(11).setCellValue(r.getIncomeAmount());
          row.createCell(12).setCellValue(r.getIncomeMode());
          row.createCell(13).setCellValue(r.getLoanTakenFrom());

          row.createCell(14).setCellValue(r.getSalaryCreditedTo());
          row.createCell(15).setCellValue(r.getEnteredAmount());
          row.createCell(16).setCellValue(r.getInterestpercent());
          row.createCell(17).setCellValue(r.getCommission());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        sheet.autoSizeColumn(13);
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(15);
        sheet.autoSizeColumn(16);
        sheet.autoSizeColumn(17);

        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println("Your excel file has been generated!");

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText("Hurrey, file exported.");
        alert.setContentText("Report table Excel file exported successfully.");
        alert.showAndWait();
      } catch (Exception ex) {
        System.out.println(ex);
      }

    }
  }

  @FXML
  public void exportButtonClicked() {
    if (data == null) {
      generateButtonClicked();
    }
    if (data != null) {
      try {
        String filename = "HotelManagementReport_" + CommonToolsClass.getDateYYYYMMDD() + ".xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("Date");
        rowhead.createCell(1).setCellValue("Expense");
        rowhead.createCell(2).setCellValue("ExpenseMode");
        rowhead.createCell(3).setCellValue("Income");
        rowhead.createCell(4).setCellValue("IncomeMode");
        rowhead.createCell(5).setCellValue("BalanceForword");
        rowhead.createCell(6).setCellValue("Balance");
        for (int i = 0; i < data.size(); i++) {
          ReportTable r = data.get(i);
          HSSFRow row = sheet.createRow((short) i + 1);
          row.createCell(0).setCellValue(r.getDate());
          row.createCell(1).setCellValue(r.getExpenseAmount());
          row.createCell(2).setCellValue(r.getExpenseMode());
          row.createCell(3).setCellValue(r.getIncomeAmount());
          row.createCell(4).setCellValue(r.getIncomeMode());
          row.createCell(5).setCellValue(r.getBalanceForword());
          row.createCell(6).setCellValue(r.getBalance());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println("Your excel file has been generated!");

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText("Hurrey, file exported.");
        alert.setContentText("Report table Excel file exported successfully.");
        alert.showAndWait();
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }
  }

  @FXML
  public void generateButtonClicked() {
    if (fromDatePicker.getValue() != null && toDatePicker.getValue() != null) {
      data =
          FXCollections.observableArrayList(getReportTableList(fromDatePicker.getValue().toString(),
              toDatePicker.getValue().toString()));
      reportTable.setItems(data);
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error Dialog");
      alert.setHeaderText("Look, an Error Dialog");
      alert.setContentText("Ooops, You did not select FROM and TO date.");
      alert.showAndWait();
    }
  }

  @FXML
  public void initialize() {
    fromDatePicker.setConverter(CommonToolsClass.getDateYYYYMMDDStringConverter());
    toDatePicker.setConverter(CommonToolsClass.getDateYYYYMMDDStringConverter());
    reportTableDateColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("date"));
    reportTableExpenseColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("expenseAmount"));
    reportTableIncomColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("incomeAmount"));
    reportTableBalanceForwordColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("balanceForword"));
    reportTableBalanceColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("balance"));

    reportTableExpenseModeColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("expenseMode"));
    reportTableIncomModeColumn
        .setCellValueFactory(new PropertyValueFactory<ReportTable, String>("incomeMode"));
  }

  public ArrayList<ReportTable> getReportTableList() {
    String getReportTableQuery = "SELECT DISTINCT * FROM " + HMConstants.HM_EXPENSE_TABLE
        + " FULL JOIN " + HMConstants.HM_INCOME_TABLE + " ON " + HMConstants.HMIncome.HM_INCOME_DATE
        + " = " + HMConstants.HMExpense.HM_EXPENSE_DATE;
    return getReportTableList(getReportTableQuery);
  }

  public ArrayList<ReportTable> getReportTableList(String fromDate, String toDate) {
    String getReportTableQuery = "SELECT DISTINCT * FROM " + HMConstants.HM_EXPENSE_TABLE
        + " FULL JOIN " + HMConstants.HM_INCOME_TABLE + " ON " + HMConstants.HMIncome.HM_INCOME_DATE
        + " = " + HMConstants.HMExpense.HM_EXPENSE_DATE + " WHERE ("
        + HMConstants.HMExpense.HM_EXPENSE_DATE + " <= '" + toDate + "' AND "
        + HMConstants.HMExpense.HM_EXPENSE_DATE + " >= '" + fromDate + "') OR ("
        + HMConstants.HMIncome.HM_INCOME_DATE + " <= '" + toDate + "' AND "
        + HMConstants.HMIncome.HM_INCOME_DATE + " >= '" + fromDate + "')" + " ORDER BY "
        + HMConstants.HMExpense.HM_EXPENSE_DATE + " ASC, " + HMConstants.HMIncome.HM_INCOME_DATE
        + " ASC";

    getReportTableQuery =
        "SELECT hUserId,item,expenseType,expenseMode,category,e.sumExpenseAmount,e.expenseDate,quantity,quantityUnit,salaryCreditedTo, iUserId,incomeType,gstPrecentage,i.sumIncomeAmount,incomeMode,loanTakenFrom,i.incomeDate,enteredAmount,interestPrecent,commission FROM (SELECT userId as iUserId,incometype,gstPrecentage, SUM(incomeAmount) sumIncomeAmount,incomeMode,loanTakenFrom, incomeDate,enteredAmount,interestPrecent,commission FROM HMINCOME GROUP BY incomeDate,userId,incomeType,gstPrecentage,incomeMode,loanTakenFrom,enteredAmount,interestPrecent,commission) i FULL JOIN (SELECT userId as hUserid,item,expenseType,expenseMode,category,SUM(expenseAmount) sumExpenseAmount,expenseDate,quantity,quantityUnit,salaryCreditedTo FROM HMEXPENSE GROUP BY expenseDate,userId,item,expenseType,expenseMode,category,quantity,quantityUnit,salaryCreditedTo) e ON i.incomeDate = e.expenseDate WHERE (expenseDate >= '"
            + fromDate + "' AND expenseDate <= '" + toDate
            + "' OR expenseDate = null) OR (incomeDate <= '" + toDate + "' AND incomeDate >= '"
            + fromDate + "' or incomeDate = null)";
    return getReportTableList(getReportTableQuery);
  }

  public ArrayList<ReportTable> getReportTableList(String getReportTableQuery) {
    ArrayList<ReportTable> reportTablesArrrayList = new ArrayList<>();
    try {
      ResultSet rs = ServerObjectImpl.getInstance().getHsqlDBObject()
          .getColumnDataResultSet(getReportTableQuery);
      double balanceForword = 0.0;
      double balance = 0.0;
      if (rs != null) {
        do {
          // 1 = E userid 1
          // 2 = item 2
          // 3 = expenseType 3
          // 4 = expense mode 13
          // 5 = category 4
          // 6 = expenseamount 12
          // 7 = expensedat 11
          // 8 = quantity 5
          // 9 = quantity unit 6
          // 10 = salaryCreditedTo 16
          // 11 = I user id 7
          // 12 = income type 8
          // 13 = gst percentage 9
          // 14 = income amount 14
          // 15 = insome mode 15
          // 16 = loan taken from 10
          // 17 = income date 11
          // 18 = entered amount 17
          // 19 = interest percentage 18
          // 20 = commission 19
          Date date = rs.getDate(7);
          if (date == null) {
            date = rs.getDate(17);
          }

          String incomeAmountString = (rs.getString(14) == null) ? "0" : rs.getString(14);
          String expenseAmountString = (rs.getString(6) == null) ? "0" : rs.getString(6);

          String incomeMode = (rs.getString(15) == null) ? "N.A" : rs.getString(15);
          String expenseMode = (rs.getString(4) == null) ? "N.A" : rs.getString(4);

          double income = Double.parseDouble(incomeAmountString);
          double expense = Double.parseDouble(expenseAmountString);
          balance = +income - expense + balanceForword;
          ReportTable reportTable = new ReportTable(rs.getString(1), rs.getString(2),
              rs.getString(3), expenseMode, rs.getString(5), expenseAmountString, date.toString(),
              rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
              rs.getString(12), rs.getString(13), incomeAmountString, incomeMode, rs.getString(16),
              rs.getString(18), rs.getString(19), rs.getString(20), String.valueOf(balanceForword),
              String.valueOf(balance));
          reportTablesArrrayList.add(reportTable);
          balanceForword = balance;
        } while (rs.next());
      } else {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No data found.");
        alert.setContentText("No data found in out database.");
        alert.showAndWait();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return reportTablesArrrayList;
  }


  public class ReportTable {
    private SimpleStringProperty expenseUserId = new SimpleStringProperty();
    private SimpleStringProperty item = new SimpleStringProperty();
    private SimpleStringProperty expenseType = new SimpleStringProperty();
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleStringProperty quantity = new SimpleStringProperty();
    private SimpleStringProperty quantityUnit = new SimpleStringProperty();
    private SimpleStringProperty incomeUserId = new SimpleStringProperty();
    private SimpleStringProperty incomeType = new SimpleStringProperty();
    private SimpleStringProperty gstPercentage = new SimpleStringProperty();
    private SimpleStringProperty loanTakenFrom = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty expenseAmount = new SimpleStringProperty();
    private SimpleStringProperty expenseMode = new SimpleStringProperty();
    private SimpleStringProperty incomeAmount = new SimpleStringProperty();
    private SimpleStringProperty incomeMode = new SimpleStringProperty();
    private SimpleStringProperty balanceForword = new SimpleStringProperty();
    private SimpleStringProperty balance = new SimpleStringProperty();

    private SimpleStringProperty salaryCreditedTo = new SimpleStringProperty();
    private SimpleStringProperty enteredAmount = new SimpleStringProperty();
    private SimpleStringProperty interestpercent = new SimpleStringProperty();
    private SimpleStringProperty commission = new SimpleStringProperty();

    public ReportTable(String expenseUserId, String item, String expenseType, String expenseMode,
        String category, String expenseAmount, String date, String quantity, String quantityUnit,
        String salaryCreditedTo, String incomeUserId, String incomeType, String gstPercentage,
        String incomeAmount, String incomeMode, String loanTakenFrom, String enteredAmount,
        String incomePercentage, String commission, String balanceForword, String balance) {
      this.expenseUserId.set((expenseUserId == null) ? "" : expenseUserId);
      this.item.set((item == null) ? "" : item);
      this.expenseType.set((expenseType == null) ? "" : expenseType);
      this.expenseMode.set((expenseMode == null) ? "" : expenseMode);
      this.category.set((category == null) ? "" : category);
      this.expenseAmount.set((expenseAmount == null) ? "" : expenseAmount);
      this.date.set((date == null) ? "" : date);
      this.quantity.set((quantity == null) ? "" : quantity);
      this.quantityUnit.set((quantityUnit == null) ? "" : quantityUnit);
      this.salaryCreditedTo.set((salaryCreditedTo == null) ? "" : salaryCreditedTo);
      this.incomeUserId.set((incomeUserId == null) ? "" : incomeUserId);
      this.incomeType.set((incomeType == null) ? "" : incomeType);
      this.gstPercentage.set((gstPercentage == null) ? "" : gstPercentage);
      this.incomeAmount.set((incomeAmount == null) ? "" : incomeAmount);
      this.incomeMode.set((incomeMode == null) ? "" : incomeMode);
      this.loanTakenFrom.set((loanTakenFrom == null) ? "" : loanTakenFrom);
      this.enteredAmount.set((enteredAmount == null) ? "" : enteredAmount);
      this.interestpercent.set((incomePercentage == null) ? "" : incomePercentage);
      this.commission.set((commission == null) ? "" : commission);
      this.balanceForword.set((balanceForword == null) ? "" : balanceForword);
      this.balance.set((balance == null) ? "" : balance);
    }


    public String getSalaryCreditedTo() {
      return salaryCreditedTo.get();
    }

    public void setSalaryCreditedTo(String salaryCreditedTo) {
      this.salaryCreditedTo.set(salaryCreditedTo);
    }

    public String getEnteredAmount() {
      return enteredAmount.get();
    }

    public void setEnteredAmount(String enteredAmount) {
      this.enteredAmount.set(enteredAmount);
    }

    public String getInterestpercent() {
      return interestpercent.get();
    }

    public void setInterestpercent(String interestpercent) {
      this.interestpercent.set(interestpercent);
    }

    public String getCommission() {
      return commission.get();
    }

    public void setCommission(String commission) {
      this.commission.set(commission);
    }

    public String getExpenseUserId() {
      return expenseUserId.get();
    }

    public void setExpenseUserId(String expenseUserId) {
      this.expenseUserId.set(expenseUserId);
    }

    public String getItem() {
      return item.get();
    }

    public void setItem(String item) {
      this.item.set(item);
    }

    public String getExpenseType() {
      return expenseType.get();
    }

    public void setExpenseType(String expenseType) {
      this.expenseType.set(expenseType);;
    }

    public String getCategory() {
      return category.get();
    }

    public void setCategory(String category) {
      this.category.set(category);;
    }

    public String getQuantity() {
      return quantity.get();
    }

    public void setQuantity(String quantity) {
      this.quantity.set(quantity);;
    }

    public String getQuantityUnit() {
      return quantityUnit.get();
    }

    public void setQuantityUnit(String quantityUnit) {
      this.quantityUnit.set(quantityUnit);
    }

    public String getIncomeUserId() {
      return incomeUserId.get();
    }

    public void setIncomeUserId(String incomeUserId) {
      this.incomeUserId.set(incomeUserId);;
    }

    public String getIncomeType() {
      return incomeType.get();
    }

    public void setIncomeType(String incomeType) {
      this.incomeType.set(incomeType);
    }

    public String getGstPercentage() {
      return gstPercentage.get();
    }

    public void setGstPercentage(String gstPercentage) {
      this.gstPercentage.set(gstPercentage);
    }

    public String getLoanTakenFrom() {
      return loanTakenFrom.get();
    }

    public void setLoanTakenFrom(String loanTakenFrom) {
      this.loanTakenFrom.set(loanTakenFrom);
    }

    public String getDate() {
      return date.get();
    }

    public void setDate(String date) {
      this.date.set(date);;
    }

    public String getExpenseAmount() {
      return expenseAmount.get();
    }

    public void setExpenseAmount(String expenseAmount) {
      this.expenseAmount.set(expenseAmount);
    }

    public String getExpenseMode() {
      return expenseMode.get();
    }

    public void setExpenseMode(String expenseMode) {
      this.expenseMode.set(expenseMode);
    }

    public String getIncomeAmount() {
      return incomeAmount.get();
    }

    public void setIncomeAmount(String incomeAmount) {
      this.incomeAmount.set(incomeAmount);
    }

    public String getIncomeMode() {
      return incomeMode.get();
    }

    public void setIncomeMode(String incomeMode) {
      this.incomeMode.set(incomeMode);
    }

    public String getBalanceForword() {
      return balanceForword.get();
    }

    public void setBalanceForword(String balanceForword) {
      this.balanceForword.set(balanceForword);
    }

    public String getBalance() {
      return balance.get();
    }

    public void setBalance(String balance) {
      this.balance.set(balance);
    }



  }
}

