package cn.edu.cqvie.iocp.control.controller;

import cn.edu.cqvie.iocp.engine.timer.TimerManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.TimerTask;

/**
 * @author ZHENG SHAOHONG
 */
public class MainController {

    @FXML
    private Label labelCount;

    public MainController() {

        // 500ms 执行一次
        TimerManager.getInstance().submit(new TimerManager.HxTimerTask() {
            int i = 0;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    labelCount.setText(String.valueOf(i++));
                });
            }
        }, 100, 500);
    }


}