package ServerObject;

import java.sql.SQLException;

import database.DatabaseToolsClass;
import database.HSQLDB;
import javafx.stage.Stage;
import login.LoginModel;

public class ServerObjectImpl implements ServerObject {

  private Stage primaryStage;
  private static ServerObjectImpl serverObjectImpl;
  private LoginModel loginModel;
  private static HSQLDB hsqlDBObject;

  @Override
  public void setPrimaryStage(Stage stage) {
    this.primaryStage = stage;
  }

  @Override
  public Stage getPrimaryStage() {
    return this.primaryStage;
  }

  public static ServerObjectImpl getInstance() {
    return ServerObjectImpl.serverObjectImpl;
  }

  public static void main(String[] args) {
    serverObjectImpl = new ServerObjectImpl();
    try {
      HSQLDB.main(null);
      hsqlDBObject = HSQLDB.getInstance();
      DatabaseToolsClass.initializeDataInHSQLDB();
    } catch (Exception e) {
      e.printStackTrace();
      hsqlDBObject = null;
    }
  }

  @Override
  public LoginModel getLoginModel() {
    return this.loginModel;
  }

  @Override
  public boolean authenticateUser(String userName, String password) {
    this.loginModel = new LoginModel(userName, password);
    return this.loginModel.isAuthenticatedUser();
  }

  @Override
  public void terminateHSQLDB() {
    try {
      hsqlDBObject.terminateHSQLDB();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public HSQLDB getHsqlDBObject() {
    return hsqlDBObject;
  }

}
