package database;

import objects.StrStr;

public class ModulesConstants {

  public static final StrStr REGISTER_NEW_USER_TAB =
      new StrStr("Register User", "register_new_user_tab");

  public static final StrStr BLOCK_USER_TAB = new StrStr("Block User", "block_user_tab");

  public static final StrStr MODIFY_RIGHTS_TAB = new StrStr("Modify Rights", "modify_rights_tab");

  public static final StrStr RESET_PASSWORD_TAB =
      new StrStr("Reset Password", "reset_password_tab");

  public static final StrStr RESET_MY_PASSWORD_TAB =
      new StrStr("Reset Your Password", "reset_my_password_tab");

  public static final StrStr[] HM_ADMIN_USER_TAB_RIGHTS_ARRAY = new StrStr[] {REGISTER_NEW_USER_TAB,
      BLOCK_USER_TAB, MODIFY_RIGHTS_TAB, RESET_PASSWORD_TAB, RESET_MY_PASSWORD_TAB};

  public static final StrStr[] HM_NORMAL_USER_TAB_RIGHTS_ARRAY =
      new StrStr[] {RESET_MY_PASSWORD_TAB};



  public static final StrStr ADD_CATEGORY_TAB = new StrStr("Category", "add_category_tab");

  public static final StrStr ADD_ITEM_TAB = new StrStr("Items", "add_item_tab");

  public static final StrStr EXPENSE_TAB = new StrStr("Expense", "expense_tab");

  public static final StrStr INCOME_TAB = new StrStr("Income", "income_tab");

  public static final StrStr REPORT_TAB = new StrStr("Report", "report_tab");

  public static final StrStr[] HM_DATA_SHEET_TAB_RIGHTS_ARRAY =
      new StrStr[] {ADD_CATEGORY_TAB, ADD_ITEM_TAB, EXPENSE_TAB, INCOME_TAB, REPORT_TAB};
}
