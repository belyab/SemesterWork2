package ru.kpfu.itis.baigulova.client.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.kpfu.itis.baigulova.client.MainClient;

import java.io.IOException;

public class WindowManager {

    public static void renderLoginWindow(Stage primaryStage) throws IOException {
        MainClient.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(WindowManager.class.getResourceAsStream("/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(1280);
        primaryStage.show();
    }

    public static void alert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

