package database;

import java.sql.SQLException;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;
import constants.HMConstants.HMExpense;
import constants.HMConstants.HMIncome;
import objects.StrStr;

public class DatabaseToolsClass {

  private static final String HM_USERS_TABLE_DEFAULT_ADMIN_ID = "a";
  private static final String HM_USERS_TABLE_DEFAULT_ADMIN_PASS = "a";
  private static final String HM_USERS_TABLE_DEFAULT_NORMAL_USER_ID = "b";
  private static final String HM_USERS_TABLE_DEFAULT_NORMAL_USER_PASS = "b";
  private static final int HM_USERS_TABLE_DEFAULT_BLOCK = 0;


  private static boolean createHMUsersTable() throws SQLException {
    String createTableQuery =
        "create table " + HMConstants.HM_USERS_TABLE + " ( " + HMConstants.HMUsers.HMUSERS_ID
            + " VARCHAR(50) primary key, " + HMConstants.HMUsers.HMUSERS_PASSWORD + " VARCHAR(20), "
            + HMConstants.HMUsers.HMUSERS_USER_BLOCK + " INT);";
    return ServerObjectImpl.getInstance().getHsqlDBObject().createTable(HMConstants.HM_USERS_TABLE,
        createTableQuery);
  }

  private static boolean createActivationKeyTable() throws SQLException {
    String createTableQuery = "create table " + HMConstants.HM_ACTIVATION_KEY_TABLE + " ( "
        + HMConstants.HMActivationKey.ACTIVATION_KEY_USED + " VARCHAR(50) primary key);";
    return ServerObjectImpl.getInstance().getHsqlDBObject()
        .createTable(HMConstants.HM_ACTIVATION_KEY_TABLE, createTableQuery);
  }

  private static boolean createHMCategoryTable() throws SQLException {
    String createTableQuery =
        "create table " + HMConstants.HM_CATEGORY_TABLE + " ( " + HMConstants.HMCategory.HM_USER_ID
            + " VARCHAR(50), " + HMConstants.HMCategory.HM_CATEGORY + " VARCHAR(500) primary key);";
    return ServerObjectImpl.getInstance().getHsqlDBObject()
        .createTable(HMConstants.HM_CATEGORY_TABLE, createTableQuery);
  }

  private static boolean createHMItemsTable() throws SQLException {
    String createTableQuery = "create table " + HMConstants.HM_ITEMS_TABLE + " ( "
        + HMConstants.HMItems.HM_USER_ID + " VARCHAR(50), " + HMConstants.HMItems.HM_ITEM_CATEGORY
        + " VARCHAR(500), " + HMConstants.HMItems.HM_ITEM + " VARCHAR(500) primary key);";
    return ServerObjectImpl.getInstance().getHsqlDBObject().createTable(HMConstants.HM_ITEMS_TABLE,
        createTableQuery);
  }

  private static boolean createHMIncomeTable() throws SQLException {
    String createTableQuery = "CREATE TABLE " + HMConstants.HM_INCOME_TABLE + " ("
        + HMIncome.HM_USER_ID + " VARCHAR(50), " + HMIncome.HM_INCOME_TYPE + " VARCHAR(50), "
        + HMIncome.HM_GST_PRECENTAGE + " INT, " + HMIncome.HM_INCOME_AMOUNT + " DECIMAL(100,2), "
        + HMIncome.HM_INCOME_MODE + " VARCHAR(50), " + HMIncome.HM_LOAN_TAKEN_FROM
        + " VARCHAR(100), " + HMIncome.HM_INCOME_DATE + " DATE, " + HMIncome.HM_ENTERED_AMOUNT
        + " DECIMAL(100,2), " + HMIncome.HM_INTEREST_PERCENT + " INT, " + HMIncome.HM_COMMISSION
        + " DECIMAL(100,2))";
    return ServerObjectImpl.getInstance().getHsqlDBObject().createTable(HMConstants.HM_INCOME_TABLE,
        createTableQuery);
  }

  private static boolean createHMExpenseTable() throws SQLException {
    String createTableQuery = "CREATE TABLE " + HMConstants.HM_EXPENSE_TABLE + " ("
        + HMExpense.HM_USER_ID + " VARCHAR(50), " + HMExpense.HM_ITEM + " VARCHAR(50), "
        + HMExpense.HM_EXPENSE_TYPE + " VARCHAR(100), " + HMExpense.HM_EXPENSE_MODE
        + " VARCHAR(500), " + HMExpense.HM_CATEGORY + " VARCHAR(50), " + HMExpense.HM_EXPENSE_AMOUNT
        + " DECIMAL(100,2), " + HMExpense.HM_EXPENSE_DATE + " DATE, "
        + HMConstants.HMExpense.HM_QUANTITY + " DECIMAL(100,2), "
        + HMConstants.HMExpense.HM_QUANTITY_UNIT + " VARCHAR(20), "
        + HMConstants.HMExpense.HM_SALARY_CREDITED_TO + " VARCHAR(100))";
    return ServerObjectImpl.getInstance().getHsqlDBObject()
        .createTable(HMConstants.HM_EXPENSE_TABLE, createTableQuery);
  }

  private static boolean createHMRightsTable() throws SQLException {
    String createTableQuery = "create table " + HMConstants.HM_RIGHTS_TABLE + " ( "
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + " VARCHAR(50), "
        + HMConstants.HMRights.HMRIGHT_TEXT + " VARCHAR(50), "
        + HMConstants.HMRights.HMRIGHT_MAIN_TAB + " VARCHAR(50));";
    return ServerObjectImpl.getInstance().getHsqlDBObject().createTable(HMConstants.HM_RIGHTS_TABLE,
        createTableQuery);
  }

  private static boolean createHMUserRightsTable() throws SQLException {
    String createTableQuery = "create table " + HMConstants.HM_USER_RIGHT_TABLE + " ( "
        + HMConstants.HMUserRights.HMRIGHT_USER_ID + " VARCHAR(50), "
        + HMConstants.HMUserRights.HMRIGHT_USER_TAB_RIGHT_IDS + " VARCHAR(500));";
    return ServerObjectImpl.getInstance().getHsqlDBObject()
        .createTable(HMConstants.HM_USER_RIGHT_TABLE, createTableQuery);
  }

  private static void insertDefaultAdminInHMUsersTable() throws SQLException {
    String insertDataQuery =
        "insert into " + HMConstants.HM_USERS_TABLE + " (" + HMConstants.HMUsers.HMUSERS_ID + ", "
            + HMConstants.HMUsers.HMUSERS_PASSWORD + "," + HMConstants.HMUsers.HMUSERS_USER_BLOCK
            + ") " + "values ('" + HM_USERS_TABLE_DEFAULT_ADMIN_ID + "', '"
            + HM_USERS_TABLE_DEFAULT_ADMIN_PASS + "'," + HM_USERS_TABLE_DEFAULT_BLOCK + ");";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertDefaultUserInHMUsersTable() throws SQLException {
    String insertDataQuery =
        "insert into " + HMConstants.HM_USERS_TABLE + " (" + HMConstants.HMUsers.HMUSERS_ID + ", "
            + HMConstants.HMUsers.HMUSERS_PASSWORD + "," + HMConstants.HMUsers.HMUSERS_USER_BLOCK
            + ") " + "values ('" + HM_USERS_TABLE_DEFAULT_NORMAL_USER_ID + "', '"
            + HM_USERS_TABLE_DEFAULT_NORMAL_USER_PASS + "'," + HM_USERS_TABLE_DEFAULT_BLOCK + ");";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertDefaultAdminUserRightsInHMUserRightTable() throws SQLException {
    String userTabRights = "";
    for (StrStr right : ModulesConstants.HM_ADMIN_USER_TAB_RIGHTS_ARRAY) {
      userTabRights = userTabRights + right.getStr2() + ",";
    }

    for (StrStr right : ModulesConstants.HM_DATA_SHEET_TAB_RIGHTS_ARRAY) {
      userTabRights = userTabRights + right.getStr2() + ",";
    }

    String insertDataQuery = "insert into " + HMConstants.HM_USER_RIGHT_TABLE + " ("
        + HMConstants.HMUserRights.HMRIGHT_USER_ID + ", "
        + HMConstants.HMUserRights.HMRIGHT_USER_TAB_RIGHT_IDS + ") " + "values ('"
        + HM_USERS_TABLE_DEFAULT_ADMIN_ID + "', '" + userTabRights + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertDefaultNormalUserRightsInHMUserRightsTable() throws SQLException {

    String normalUserRights = "";
    for (StrStr right : ModulesConstants.HM_NORMAL_USER_TAB_RIGHTS_ARRAY) {
      normalUserRights = normalUserRights + right.getStr2() + ",";
    }

    for (StrStr right : ModulesConstants.HM_DATA_SHEET_TAB_RIGHTS_ARRAY) {
      normalUserRights = normalUserRights + right.getStr2() + ",";
    }

    String insertDataQuery = "insert into " + HMConstants.HM_USER_RIGHT_TABLE + " ("
        + HMConstants.HMUserRights.HMRIGHT_USER_ID + ", "
        + HMConstants.HMUserRights.HMRIGHT_USER_TAB_RIGHT_IDS + ") " + "values ('"
        + HM_USERS_TABLE_DEFAULT_NORMAL_USER_ID + "', '" + normalUserRights + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertNewUserTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.REGISTER_NEW_USER_TAB.getStr2() + "','"
        + ModulesConstants.REGISTER_NEW_USER_TAB.getStr1() + "','" + HMConstants.HM_MAIN_USER_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertResetPasswordTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.RESET_PASSWORD_TAB.getStr2() + "','"
        + ModulesConstants.RESET_PASSWORD_TAB.getStr1() + "','" + HMConstants.HM_MAIN_USER_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertResetMyPasswordTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.RESET_MY_PASSWORD_TAB.getStr2() + "','"
        + ModulesConstants.RESET_MY_PASSWORD_TAB.getStr1() + "','" + HMConstants.HM_MAIN_USER_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertBlockUserTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.BLOCK_USER_TAB.getStr2() + "','"
        + ModulesConstants.BLOCK_USER_TAB.getStr1() + "','" + HMConstants.HM_MAIN_USER_TAB + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }


  private static void insertModifyRightsTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.MODIFY_RIGHTS_TAB.getStr2() + "','"
        + ModulesConstants.MODIFY_RIGHTS_TAB.getStr1() + "','" + HMConstants.HM_MAIN_USER_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertAddCategoryTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.ADD_CATEGORY_TAB.getStr2() + "','"
        + ModulesConstants.ADD_CATEGORY_TAB.getStr1() + "','" + HMConstants.HM_MAIN_DATA_SHEET_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertAddItemTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.ADD_ITEM_TAB.getStr2() + "','"
        + ModulesConstants.ADD_ITEM_TAB.getStr1() + "','" + HMConstants.HM_MAIN_DATA_SHEET_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertEmpenseTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.EXPENSE_TAB.getStr2() + "','"
        + ModulesConstants.EXPENSE_TAB.getStr1() + "','" + HMConstants.HM_MAIN_DATA_SHEET_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertIncomeTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.INCOME_TAB.getStr2() + "','"
        + ModulesConstants.INCOME_TAB.getStr1() + "','" + HMConstants.HM_MAIN_DATA_SHEET_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertReportTabRight() throws SQLException {
    String insertDataQuery = "insert into " + HMConstants.HM_RIGHTS_TABLE + " ("
        + HMConstants.HMRights.HMRIGHT_RIGHT_DIRECTORY_KRY + ", "
        + HMConstants.HMRights.HMRIGHT_TEXT + ", " + HMConstants.HMRights.HMRIGHT_MAIN_TAB + ") "
        + "values ('" + ModulesConstants.REPORT_TAB.getStr2() + "','"
        + ModulesConstants.REPORT_TAB.getStr1() + "','" + HMConstants.HM_MAIN_DATA_SHEET_TAB
        + "');";
    ServerObjectImpl.getInstance().getHsqlDBObject().executeQuery(insertDataQuery);
  }

  private static void insertDefaultRightsInHMRightsTable() throws SQLException {
    insertNewUserTabRight();
    insertResetPasswordTabRight();
    insertResetMyPasswordTabRight();
    insertBlockUserTabRight();
    insertModifyRightsTabRight();
    /// Data Sheet
    insertAddCategoryTabRight();
    insertAddItemTabRight();
    insertEmpenseTabRight();
    insertIncomeTabRight();
    insertReportTabRight();
  }

  public static void initializeDataInHSQLDB() {
    try {
      if (createHMUsersTable()) {
        insertDefaultAdminInHMUsersTable();
        insertDefaultUserInHMUsersTable();
      }
      if (createHMUserRightsTable()) {
        insertDefaultAdminUserRightsInHMUserRightTable();
        insertDefaultNormalUserRightsInHMUserRightsTable();
      }
      if (createHMRightsTable()) {
        insertDefaultRightsInHMRightsTable();
      }
      createActivationKeyTable();
      createHMCategoryTable();
      createHMItemsTable();
      createHMIncomeTable();
      createHMExpenseTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }
}
