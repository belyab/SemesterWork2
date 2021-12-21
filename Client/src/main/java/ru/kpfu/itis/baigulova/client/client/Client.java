package ru.kpfu.itis.baigulova.client.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Client {

    private static Client client = new Client();
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public Client() {
        try {
            this.socket = new Socket("localhost", 8082);
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static Client getInstance() {
        return client;
    }


    public boolean auth(String name, String pass) throws IOException {
        output.writeUTF("/auth " + name + " " + pass);

        String response = input.readUTF();
        System.out.println(response);
        return response.startsWith("/auth_success");
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

    public Integer[] getGameMap() throws IOException {
        sendMessage("/getmap");
        String msg = input.readUTF();
        System.out.println("/getmap");
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(msg, Integer[].class);
    }

    public Integer[] getStartGameMap() throws IOException {
        sendMessage("/map");
        String msg = input.readUTF();
        System.out.println("/map");
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(msg, Integer[].class);
    }

    public int waitBackFromServer() {
        try {
            String msg = input.readUTF();
            return Integer.parseInt(msg.split("/backmap ")[1].trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean whoseStage() throws IOException {
        sendMessage("/whom");
        String msg = input.readUTF();
        return msg.startsWith("true");
    }

    public String waitQuestion() throws IOException {
        System.out.println("tut");
        while (true) {
            System.out.println("tut2");
            String response = input.readUTF();
            System.out.println("mess: " + response);
            return response;
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

