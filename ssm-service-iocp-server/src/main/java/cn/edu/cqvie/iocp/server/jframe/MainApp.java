package cn.edu.cqvie.iocp.server.jframe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ZHENG SHAOHONG
 */
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());

        primaryStage.setTitle("Message Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}