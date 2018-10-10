package constants;

public final class HMConstants {
  public static final String HM_USERS_TABLE = "HMUSERS";
  public static final String HM_USER_RIGHT_TABLE = "HMUSERRIGHTS";
  public static final String HM_RIGHTS_TABLE = "HMRIGHTS";
  public static final String HM_CATEGORY_TABLE = "HMCATEGORY";
  public static final String HM_ITEMS_TABLE = "HMITEMS";
  public static final String HM_INCOME_TABLE = "HMINCOME";
  public static final String HM_EXPENSE_TABLE = "MEXPENSE";
  public static final String HM_MAIN_USER_TAB = "user";
  public static final String HM_MAIN_DATA_SHEET_TAB = "data_sheet";

  public final class HMUsers {
    public static final String HMUSERS_ID = "id";
    public static final String HMUSERS_PASSWORD = "password";
    public static final String HMUSERS_USER_BLOCK = "block";
  }

  public final class HMRights {
    public static final String HMRIGHT_RIGHT_DIRECTORY_KRY = "rightDirectoryKey";
    public static final String HMRIGHT_TEXT = "rightText";
    public static final String HMRIGHT_MAIN_TAB = "mainCategory";
  }

  public final class HMUserRights {
    public static final String HMRIGHT_USER_ID = "userId";
    public static final String HMRIGHT_USER_TAB_RIGHT_IDS = "userTabRightsId";
  }
  public final class HMCategory {
    public static final String HM_USER_ID = "userId";
    public static final String HM_CATEGORY = "category";
  }
  public final class HMItems {
    public static final String HM_USER_ID = "userId";
    public static final String HM_ITEM_CATEGORY = "itemCategory";
    public static final String HM_ITEM = "item";
  }
  public final class HMIncome {
    public static final String HM_USER_ID = "userId";
    public static final String HM_INCOME_TYPE = "incomeType";
    public static final String HM_GST_PRECENTAGE = "gstPrecentage";
    public static final String HM_AMOUNT = "amount";
    public static final String HM_INCOME_MODE = "incomeMode";
    public static final String HM_LOAN_TAKEN_FROM = "loanTakenFrom";
  }
  public final class HMExpense {
    public static final String HM_USER_ID = "userId";
    public static final String HM_EXPENSE_TYPE = "expenseType";
    public static final String HM_CATEGORY = "category";
    public static final String HM_ITEM = "item";
    public static final String HM_AMOUNT = "amount";
    public static final String HM_EXPENSE_MODE = "expenseMode";
  }
}
