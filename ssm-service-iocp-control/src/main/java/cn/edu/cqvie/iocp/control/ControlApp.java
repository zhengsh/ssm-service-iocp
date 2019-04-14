package cn.edu.cqvie.iocp.control;

import cn.edu.cqvie.iocp.server.MessageServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ZHENG SHAOHONG
 */
public class ControlApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
        Scene scene = new Scene(root, 500, 160);
        //scene.getStylesheets().add(getClass().getClassLoader().getResource("view/application.css").toExternalForm());

        Properties properties = new Properties();
        InputStream in = ControlApp.class.getClassLoader().getResourceAsStream("control.properties");
        properties.load(in);
        // 读取服务器配置端口信息
        int port = Integer.parseInt(properties.getProperty("hx.server.port", "12345"));

        DamonThread.newThread(new DamonThread () {
            @Override
            public void run() {
                try {
                    MessageServer.start(port);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        primaryStage.setTitle("message control : " + port);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 后台线程
     */
    public static class DamonThread implements Runnable {

        @Override
        public void run() {

        }

        public static Thread newThread(DamonThread t) {
            Thread thread = new Thread(t, "message-server");
            thread.setDaemon(true);
            return thread;
        }
    }
}