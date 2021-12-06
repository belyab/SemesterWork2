package ru.kpfu.itis.baigulova.server.Task;

import ru.kpfu.itis.baigulova.server.model.Answer;
import ru.kpfu.itis.baigulova.server.model.Question;
import ru.kpfu.itis.baigulova.server.server.ClientThread;
import ru.kpfu.itis.baigulova.server.service.GameService;

public class GameTask {

    private ClientThread player1;
    private ClientThread player2;

    private int[] terrains = new int[]{1, 1};
    private Integer[] gameMapPlayer1;
    private Integer[] gameMapPlayer2;
    private Question currentQuestion = null;
    private int attackPosition = -1;
    private long currentTime = 0L;
    private int whoseMove = 0;
    private Answer[] answers = new Answer[]{null, null};
    private boolean gameIsEndInThisThread = false;
    private int whoseWin = -1;

    public GameTask(ClientThread player1, ClientThread player2) {
        this.player1 = player1;
        this.player2 = player2;

        player1.setPositionOnMap(0);
        player2.setPositionOnMap(1);

        gameMapPlayer1 = GameService.getGameMap(0);
        gameMapPlayer2 = GameService.getGameMap(1);
    }


}
