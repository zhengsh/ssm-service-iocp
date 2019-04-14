package cn.edu.cqvie.iocp.control;

import cn.edu.cqvie.iocp.client.MessageClient;
import cn.edu.cqvie.iocp.engine.pool.ThreadPool;
import cn.edu.cqvie.iocp.server.MessageServer;
import cn.edu.cqvie.iocp.simu.SimuTask;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * @author ZHENG SHAOHONG
 */
public class ControlApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(ControlApp.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
        Scene scene = new Scene(root, 666, 160);

        Properties properties = new Properties();
        InputStream in = ControlApp.class.getClassLoader().getResourceAsStream("control.properties");
        properties.load(in);
        // 读取服务器配置端口信息
        int port = Integer.parseInt(properties.getProperty("hx.server.port", "12345"));
        int simu = Integer.parseInt(properties.getProperty("hx.simu.num", "1"));

        //  服务线程
        DamonThread.submit(() -> {
            try {
                MessageServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 模拟客户端线程
        primaryStage.setTitle("message control : " + port);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            MessageServer.stop();
            logger.info("message server is stop");
        });

        DamonThread.submit(() -> {
            try {
                while (!MessageServer.isOpen()) {
                    Thread.sleep(500);
                }
                for (int i = 0; i < simu; i++) {
                    SimuTask.getInstance().submit(new MessageClient());
                    Thread.sleep(50);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 后台线程
     */
    private static class DamonThread {

        static void submit(Runnable t) {
            newThread(t).start();
        }

        static Thread newThread(Runnable t) {
            Thread thread = new Thread(t, "message-server");
            thread.setDaemon(true);
            return thread;
        }
    }
}