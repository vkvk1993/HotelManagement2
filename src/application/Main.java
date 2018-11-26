package application;

import ServerObject.ServerObjectImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    try {
      VBox root = (VBox) FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
      Scene scene = new Scene(root, 600, 390);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Hotel Management System V.1");
      primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("1.png")));
      primaryStage.show();
      primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

        @Override
        public void handle(WindowEvent event) {
          Platform.runLater(new Runnable() {

            @Override
            public void run() {
              ServerObjectImpl.getInstance().terminateHSQLDB();
              System.out.println("Application Closed by click to Close Button(X)");
              System.exit(0);
            }
          });
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    ServerObjectImpl.getInstance().setPrimaryStage(primaryStage);
  }

  public static void main(String[] args) {
    ServerObjectImpl.main(null);
    launch(args);
  }
}
