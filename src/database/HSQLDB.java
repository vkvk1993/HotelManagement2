package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.hsqldb.Server;


public class HSQLDB {
  private static final Logger logger = log.Logger.getLogger(HSQLDB.class.getName());
  private static Server hsqlServer = null;
  private static Connection connection = null;
  private static HSQLDB hsqldb = null;
  static {
    try {
      hsqldb = new HSQLDB();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static HSQLDB getInstance() {
    return hsqldb;
  }

  private HSQLDB() throws ClassNotFoundException, SQLException {
    this.initHSQLDB();
  }

  private void initHSQLDB() throws ClassNotFoundException, SQLException {
    hsqlServer = new Server();
    hsqlServer.setLogWriter(null);
    hsqlServer.setSilent(true);
    hsqlServer.setDatabaseName(0, "xdb");
    hsqlServer.setDatabasePath(0, "file:DBFiles/testdb");
    hsqlServer.start();
    logger.info("HSQLDB Connections established successfully.");
    Class.forName("org.hsqldb.jdbcDriver");
    connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "sa", "");
  }


  public boolean createTable(String tableName, String createTableQuery) throws SQLException {
    DatabaseMetaData dbm = connection.getMetaData();
    ResultSet tables = dbm.getTables(null, null, tableName, null);
    if (!tables.next()) {
      logger.info(tableName + " table is absent.");
      connection.prepareStatement(createTableQuery).execute();
      return true;
    }
    return false;
  }

  public boolean executeQuery(String query) throws SQLException {
    connection.prepareStatement(query).execute();
    return true;
  }


  public void terminateHSQLDB() throws SQLException {
    if (connection != null) {
      logger.info("Closing connection.");
      connection.close();
    }
    if (hsqlServer != null) {
      logger.info("Stopping the HSQL Server.");
      hsqlServer.stop();
    }
  }

  public String getDBTablesData(String selectQuery) throws SQLException {
    ResultSet rs = connection.prepareStatement(selectQuery).executeQuery();
    String returnData = "";
    while (rs.next()) {
      returnData += "Id: " + rs.getInt(1) + " Name: " + rs.getString(2) + "\n";
    }
    return returnData;
  }

  public boolean isDataPresentInDB(String query) throws SQLException {
    ResultSet rs = connection.prepareStatement(query).executeQuery();
    while (rs.next()) {
      return true;
    }
    return false;
  }

  public String getColumnData(String query, int columnNumber) throws SQLException {
    ResultSet rs = connection.prepareStatement(query).executeQuery();
    while (rs.next()) {
      return rs.getString(columnNumber);
    }
    return null;
  }

  public ResultSet getColumnDataResultSet(String query) throws SQLException {
    ResultSet rs = connection.prepareStatement(query).executeQuery();
    while (rs.next()) {
      return rs;
    }
    return null;
  }

  public static void main(String[] args) {}

}
