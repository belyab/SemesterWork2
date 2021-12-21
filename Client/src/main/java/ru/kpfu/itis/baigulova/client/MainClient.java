package ru.kpfu.itis.baigulova.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kpfu.itis.baigulova.client.client.Client;

public class MainClient extends Application {

    public static Stage primaryStage;
    private TextArea messages = new TextArea();
    private boolean isServer = false;
    Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage secondaryStage = new Stage();
        MainClient.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        FXMLLoader loaderChat = new FXMLLoader();
        Parent rootChat = loaderChat.load(getClass().getResourceAsStream("/fxml/chat.fxml"));
        Parent root = loader.load(getClass().getResourceAsStream("/fxml/login.fxml"));
        secondaryStage.setScene(new Scene(rootChat));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(100);
        primaryStage.setMinWidth(900);
        primaryStage.show();
        secondaryStage.show();
    }

}
