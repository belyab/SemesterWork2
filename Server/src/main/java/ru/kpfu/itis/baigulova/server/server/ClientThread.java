package ru.kpfu.itis.baigulova.server.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.baigulova.server.Task.GameTask;
import ru.kpfu.itis.baigulova.server.service.GameService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Server server;
    private DataInputStream input;
    private DataOutputStream output;

    private Boolean isActive = Boolean.TRUE;
    private Boolean isGame = Boolean.FALSE;

    private int positionOnMap;
    private GameTask gameTask;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getGame() {
        return isGame;
    }

    public void setGame(Boolean game) {
        isGame = game;
    }

    public int getPositionOnMap() {
        return positionOnMap;
    }

    public void setPositionOnMap(int positionOnMap) {
        this.positionOnMap = positionOnMap;
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    public void setGameTask(GameTask gameTask) {
        this.gameTask = gameTask;
    }

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            isActive = Boolean.FALSE;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = input.readUTF();
                System.out.println(msg);
                gameCommands(msg);
            } catch (IOException e) {
                e.printStackTrace();
                isActive = Boolean.FALSE;
                break;
            }
        }
    }

    private void gameCommands(String msg) {
        String[] command = msg.trim().split(" ");
        if (command[0].equals("/map")) {
            sendMsg(GameService.gameMapToString(gameTask.getMapByPos(positionOnMap)));

        } else if (command[0].equals("/getmap")) {
            sendMsg(GameService.gameMapToString(gameTask.getMapByPos(positionOnMap)));

        } else if (command[0].equals("/whom")) {
            sendMsg(String.valueOf((gameTask.getWhoseMove() == positionOnMap)));

        } else if (command[0].equals("/attack")) {
            try {
                final StringWriter sw = new StringWriter();
                final ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(sw, gameTask.getCurrentQuestion());

                gameTask.setAttackPosition(Integer.parseInt(command[1]));
                gameTask.setAnswerStart();

                gameTask.sendAttackToPlayers(sw.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command[0].equals("/answer")) {
            System.out.println("answer thread" + this.getThreadGroup().getName());
            gameTask.getAnswers()[positionOnMap].gameEnd();
            gameTask.getAnswers()[positionOnMap].setRight(gameTask.getCurrentQuestion()
                    .checkAnswer(Integer.parseInt(command[1])));
            gameTask.checkEndGame();

        }
    }


    public void sendMsg(String msg) {
        try {
            output.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
