package ru.kpfu.itis.baigulova.server.server;

import ru.kpfu.itis.baigulova.server.Task.GameTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private Server server;
    private DataInputStream input;
    private DataOutputStream output;

    private int positionOnMap;
    private GameTask gameTask;

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        try{
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGameTask(GameTask gameTask) {
        this.gameTask = gameTask;
    }

    public void setPositionOnMap(int positionOnMap) {
        this.positionOnMap = positionOnMap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = input.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    public void sendMessage(String msg) {
        try {
            output.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
