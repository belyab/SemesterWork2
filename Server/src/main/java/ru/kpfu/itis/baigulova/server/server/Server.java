package ru.kpfu.itis.baigulova.server.server;

import lombok.SneakyThrows;
import ru.kpfu.itis.baigulova.server.Task.AuthenticationTask;
import ru.kpfu.itis.baigulova.server.Task.GameTask;
import ru.kpfu.itis.baigulova.server.service.AuthenticationService;
import ru.kpfu.itis.baigulova.server.service.QuestionService;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private List<ClientThread> clientThreads = Collections.synchronizedList(new ArrayList<>());
    private ConcurrentLinkedQueue<ClientThread> queueClients = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        try {
            QuestionService.setQuestions("/quiz.csv");

            ServerSocket serverSocket = new ServerSocket(8082);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("client connected");
                new AuthenticationTask(socket, this, new AuthenticationService()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void subscribe(ClientThread clientThread) {

        if (queueClients.size() > 0) {
            System.out.println("tut");
            ClientThread oldClientThread = queueClients.poll();

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


            oldClientThread.sendMsg("/foundgame");
            clientThread.sendMsg("/foundgame");
        }
        else
            queueClients.add(clientThread);


    }

    public void broadcast(String msg) {
        clientThreads.stream().filter(ClientThread::getActive).forEach(clientThread -> {
            System.out.println("/test");
            System.out.println(msg);
            clientThread.sendMsg(msg);
        });
    }
}
