package ru.kpfu.itis.baigulova.server.Task;

import ru.kpfu.itis.baigulova.server.model.Client;
import ru.kpfu.itis.baigulova.server.server.ClientThread;
import ru.kpfu.itis.baigulova.server.server.Server;
import ru.kpfu.itis.baigulova.server.service.AuthenticationService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthenticationTask extends Thread{

    private Socket socket;
    private Server server;
    private AuthenticationService authenticationService;

    private DataInputStream input;
    private DataOutputStream output;

    public AuthenticationTask(Socket socket, Server server , AuthenticationService authenticationService) {
        this.socket = socket;
        this.server = server;
        this.authenticationService = authenticationService;
    }

    @Override
    public void run() {
        try{
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String message = input.readUTF();
                System.out.println(message);

                if(message.startsWith("/auth")){
                    String[] creds = message.split(" ");
                    Client client = authenticationService.authenticate(creds[1], creds[2]);
                    if (client != null) {
                        output.writeUTF("/auth_success");
                        server.subscribe(new ClientThread(server, socket));
                        break;
                    } else {
                        output.writeUTF("/auth_failed");
                    }

                } else {
                    output.writeUTF("/unauthorized");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
