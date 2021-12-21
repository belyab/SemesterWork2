package ru.kpfu.itis.baigulova.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.baigulova.client.MainClient;
import ru.kpfu.itis.baigulova.client.client.Client;
import ru.kpfu.itis.baigulova.client.window.WindowManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController {

    @FXML
    TextArea messages;
    @FXML
    TextField input;
    @FXML
    Button send;

    private boolean isServer = true;

    public void send(ActionEvent actionEvent) throws IOException {
        Platform.runLater(() -> {
            Client client = Client.getInstance();
            String message = isServer ? "Игрок1: " : "Игрок2: ";
            message += input.getText();
            input.clear();
            messages.appendText(message + "\n");
            try {
                client.sendMessage(message);
            } catch (Exception e) {
                messages.appendText("Failed to send\n");
            }
        });
    }

}
