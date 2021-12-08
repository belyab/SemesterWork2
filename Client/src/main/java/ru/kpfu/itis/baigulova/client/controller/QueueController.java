package ru.kpfu.itis.baigulova.client.controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import ru.kpfu.itis.baigulova.client.MainClient;
import ru.kpfu.itis.baigulova.client.client.Client;
import ru.kpfu.itis.baigulova.client.window.WindowManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QueueController implements Initializable {
    public ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        new Thread(() -> {
            Client client = Client.getClient();
            try {
                int command = client.getCommands();
                if (command==1){
                    Platform.runLater(() -> {
                        try {
                            WindowManager.renderGameWindow(MainClient.primaryStage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

