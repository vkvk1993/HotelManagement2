package login;

import ServerObject.ServerObjectImpl;
import constants.HMConstants;

public class LoginModel {
  private String userId = null;
  private boolean authenticatedUser = false;

  public boolean isAuthenticatedUser() {
    return this.authenticatedUser;
  }

  public void setAuthenticatedUser(boolean authenticatedUser) {
    this.authenticatedUser = authenticatedUser;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public LoginModel(String userName, String password) {
    loginDataValidation(userName, password);
  }

  public void loginDataValidation(String id, String password) {
    try {
      String validateLoginQuery = "select * from " + HMConstants.HM_USERS_TABLE + " where id = '"
          + id + "' AND password = '" + password + "';";
      String userId =
          ServerObjectImpl.getInstance().getHsqlDBObject().getColumnData(validateLoginQuery, 1);
      if (userId != null) {
        setUserId(id);
        setAuthenticatedUser(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
