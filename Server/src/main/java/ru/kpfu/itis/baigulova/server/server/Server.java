package ru.kpfu.itis.baigulova.server.server;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server extends Thread {

    private static final int PORT = 5556;
    private ServerSocket serverSocket;
    private List<ClientThread> clientThreads = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void run() {
        try {
            //Создание вопроса

            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                //авторизация или вход
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
