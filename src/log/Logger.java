package log;

import java.io.File;
import java.util.logging.FileHandler;

public class Logger {

  public static java.util.logging.Logger getLogger(String className) {
    java.util.logging.Logger returnLog = java.util.logging.Logger.getLogger(className);
    try {
      new File("CreationLog.log").createNewFile();
      FileHandler fileHandler = new FileHandler("CreationLog.log");
      returnLog.addHandler(fileHandler);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnLog;
  }
}
