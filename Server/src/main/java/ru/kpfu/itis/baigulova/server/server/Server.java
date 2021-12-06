package ru.kpfu.itis.baigulova.server.server;

import lombok.SneakyThrows;
import ru.kpfu.itis.baigulova.server.Task.GameTask;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread {

    private static final int PORT = 5557;
    private ServerSocket serverSocket;
    private List<ClientThread> clientThreads = Collections.synchronizedList(new ArrayList<>());
    private ConcurrentLinkedQueue<ClientThread> queue = new ConcurrentLinkedQueue<>();

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

    @SneakyThrows
    public void subscribe(ClientThread clientThread) {
        if (queue.size() > 0) {
            System.out.println("tut");
            ClientThread oldClientThread = queue.poll();

            assert oldClientThread != null;

            GameTask gameTask = new GameTask(
                    oldClientThread,
                    clientThread
            );
            oldClientThread.setGameTask(gameTask);
            clientThread.setGameTask(gameTask);


            oldClientThread.start();
            clientThreads.add(oldClientThread);


            clientThread.start();
            clientThreads.add(clientThread);


            oldClientThread.sendMessage("/foundgame");
            clientThread.sendMessage("/foundgame");
        }
        else
            queue.add(clientThread);
    }
}
