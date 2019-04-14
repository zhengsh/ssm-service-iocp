package cn.edu.cqvie.iocp.control.controller;

import cn.edu.cqvie.iocp.client.content.ControlContent;
import cn.edu.cqvie.iocp.engine.timer.TimerManager;
import cn.edu.cqvie.iocp.server.control.ServerControl;
import cn.edu.cqvie.iocp.simu.SimuTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author ZHENG SHAOHONG
 */
public class MainController {

    @FXML
    private Label labelCount;

    @FXML
    private Label labelTime;

    @FXML
    private Label labelclientCount;

    public MainController() {

        // 500ms 执行一次
        TimerManager.getInstance().submit(new TimerManager.HxTimerTask() {
            int i = 0;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    ServerControl control = ServerControl.getInstance();
                    int count = control.get();
                    labelCount.setText(String.valueOf(count));

                    SimuTask task = SimuTask.getInstance();
                    int clientCount = task.count();
                    labelclientCount.setText(String.valueOf(clientCount));

                    ControlContent instance1 = ControlContent.getInstance();
                    labelTime.setText(String.valueOf(instance1.max()));
                });
            }
        }, 100, 500);
    }


}