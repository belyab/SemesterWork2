package ru.kpfu.itis.baigulova.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.kpfu.itis.baigulova.client.MainClient;
import ru.kpfu.itis.baigulova.client.client.Client;
import ru.kpfu.itis.baigulova.client.window.WindowManager;

import java.io.IOException;

public class LoginController {
    public PasswordField passwordField;
    public TextField loginField;
    public Button loginButton;


    public void login(ActionEvent actionEvent) throws IOException {
        Platform.runLater(() -> {
            Client client = Client.getClient();
            boolean auth = false;
            try {
                auth = client.auth(loginField.getText(), passwordField.getText());
                if (auth) {
                    WindowManager.renderQueueWindow(MainClient.primaryStage);
                } else {
                    WindowManager.alert("Ошибка", "Неверный логин или пароль");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

