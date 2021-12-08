package ru.kpfu.itis.baigulova.client.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static Client client = new Client();
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public Client() {
        try {
            this.socket = new Socket("localhost", 5557);
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean auth(String name, String pass) throws IOException {
        output.writeUTF("/auth " + name + " " + pass);

        String response = input.readUTF();
        System.out.println(response);
        return response.startsWith("/auth_success");
    }

    public static Client getClient() {
        return client;
    }

    public void  sendMessage(String message) throws IOException {
        output.writeUTF(message);
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCommands() throws IOException {
        while (true) {
            String message = input.readUTF();
            System.out.println(message);
            if (message.startsWith("/foundgame")) {
                return 1;
            } else if (message.startsWith("/defend")) {
                return Integer.parseInt(message.split(" ")[1]);
            } else
                System.out.println(message);
        }
    }
}

