package ru.kpfu.itis.baigulova.server.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Server server;
    private DataInputStream input;
    private DataOutputStream output;

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        try{
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
