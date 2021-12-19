package com.example.chat.client;

import com.example.chat.client.client.ChatClient;
import com.example.chat.client.model.UserConfig;
import com.example.chat.client.view.BaseView;
import com.example.chat.client.view.ChatView;
import com.example.chat.client.view.UserConfigView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {

    Stage primaryStage;

    private BorderPane rootLayout;

    private UserConfig userConfig;

    private ChatClient chatClient;

    private ChatView chatView;

    private UserConfigView userConfigView;

    public ChatView getChatView() {
        return chatView;
    }

    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }

    public UserConfigView getUserConfigView() {
        return userConfigView;
    }

    public void setUserConfigView(UserConfigView userConfigView) {
        this.userConfigView = userConfigView;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public UserConfig getUserConfig() {
        return userConfig;
    }

    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    public void startChatClient() throws IOException {
        getChatClient().start();
    }

    public void setView(BaseView view) {
        rootLayout.setCenter(view.getView());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chat");
        this.primaryStage.setOnCloseRequest(e-> System.exit(0));

        chatClient = new ChatClient(this);

        BaseView.setApplication(this);

        userConfigView = new UserConfigView();
        chatView = new ChatView();

        this.initLayout();
    }

    private void initLayout(){
        rootLayout = new BorderPane();

        Scene scene = new Scene(rootLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        this.setView(getUserConfigView());
    }

    public void appendMessageToChat(String message) {
        chatView.appendMessageToConversation(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
