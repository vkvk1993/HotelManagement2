package ServerObject;

import database.HSQLDB;
import javafx.stage.Stage;
import login.LoginModel;

public interface ServerObject {
  public void setPrimaryStage(Stage stage);

  public Stage getPrimaryStage();

  public boolean authenticateUser(String userName, String password);

  public LoginModel getLoginModel();

  void terminateHSQLDB();

  HSQLDB getHsqlDBObject();

}
