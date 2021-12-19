package ru.kpfu.itis.baigulova.server.Task;

import ru.kpfu.itis.baigulova.server.model.Answer;
import ru.kpfu.itis.baigulova.server.model.Question;
import ru.kpfu.itis.baigulova.server.server.ClientThread;
import ru.kpfu.itis.baigulova.server.service.GameService;
import ru.kpfu.itis.baigulova.server.service.QuestionService;

import java.util.Arrays;

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

    public ClientThread getPlayer1() {
        return player1;
    }

    public void setPlayer1(ClientThread player1) {
        this.player1 = player1;
    }

    public ClientThread getPlayer2() {
        return player2;
    }

    public void setPlayer2(ClientThread player2) {
        this.player2 = player2;
    }

    public int[] getTerrains() {
        return terrains;
    }

    public void setTerrains(int[] terrains) {
        this.terrains = terrains;
    }

    public Integer[] getGameMapPlayer1() {
        return gameMapPlayer1;
    }

    public void setGameMapPlayer1(Integer[] gameMapPlayer1) {
        this.gameMapPlayer1 = gameMapPlayer1;
    }

    public Integer[] getGameMapPlayer2() {
        return gameMapPlayer2;
    }

    public void setGameMapPlayer2(Integer[] gameMapPlayer2) {
        this.gameMapPlayer2 = gameMapPlayer2;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getAttackPosition() {
        return attackPosition;
    }

    public void setAttackPosition(int attackPosition) {
        this.attackPosition = attackPosition;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getWhoseMove() {
        return whoseMove;
    }

    public void setWhoseMove(int whoseMove) {
        this.whoseMove = whoseMove;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }

    public boolean isGameIsEndInThisThread() {
        return gameIsEndInThisThread;
    }

    public void setGameIsEndInThisThread(boolean gameIsEndInThisThread) {
        this.gameIsEndInThisThread = gameIsEndInThisThread;
    }

    public int getWhoseWin() {
        return whoseWin;
    }

    public void setWhoseWin(int whoseWin) {
        this.whoseWin = whoseWin;
    }

    private void forNextGame() {
        currentQuestion = null;
        gameIsEndInThisThread = false;
        for (int i = 0; i < 2; i++) {
            answers[i] = null;
        }
    }

    public void checkEndGame() {
        if (gameIsEndInThisThread) {
            return;
        }

        boolean isEnd = true;
        for (int i = 0; i < 2; i++) {
            if (!answers[i].isGameEnd) {
                isEnd = false;
                break;
            }
        }
        if (isEnd) {
            gameIsEndInThisThread = true;
            checkAnswers();
        }
    }

    private void checkAnswers() {
        if (answers[0].getEndTime() > answers[1].getEndTime()) {
            if (answers[0].isRight()) {
                terrains[0]++;
                gameMapPlayer1[attackPosition] = 1;
                gameMapPlayer2[attackPosition] = 2;
                whoseWin = 0;
            } else if (answers[1].isRight()) {
                terrains[1]++;
                gameMapPlayer1[attackPosition] = 2;
                gameMapPlayer2[attackPosition] = 1;
                whoseWin = 1;
            }
        } else {
            if (answers[1].isRight()) {
                terrains[1]++;
                gameMapPlayer1[attackPosition] = 2;
                gameMapPlayer2[attackPosition] = 1;
                whoseWin = 1;
            } else if (answers[0].isRight()) {
                terrains[0]++;
                gameMapPlayer1[attackPosition] = 1;
                gameMapPlayer2[attackPosition] = 2;
                whoseWin = 0;
            }
        }
        getBack();
    }

    private void getBack() {
        if (whoseMove == 0)
            whoseMove = 1;
        else
            whoseMove = 0;
        System.out.println("terrains");
        System.out.println(Arrays.toString(terrains));
        if (terrains[0]+terrains[1]==9){
            if (terrains[0]>terrains[1]){
                player1.sendMsg("/backmap 2");
                player2.sendMsg("/backmap 3");
            }
            else if (terrains[0]<terrains[1]){
                player1.sendMsg("/backmap 3");
                player2.sendMsg("/backmap 2");
            }
            else {
                player1.sendMsg("/backmap 4");
                player2.sendMsg("/backmap 4");
            }
            return;
        }

        if (whoseWin == 0) {
            player1.sendMsg("/backmap 1");
        } else {
            player1.sendMsg("/backmap 0");
        }

        if (whoseWin == 1) {
            player2.sendMsg("/backmap 1");
        } else {
            player2.sendMsg("/backmap 0");
        }


        forNextGame();
    }

    public void sendAttackToPlayers(String msg) {
        player1.sendMsg(msg);
        player2.sendMsg(msg);
    }

    public void setAnswerStart() {
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            answers[i] = new Answer(currentTime);
        }
    }

    public Integer[] getMapByPos(int pos) {
        if (pos == 0) return gameMapPlayer1;
        if (pos == 1) return gameMapPlayer2;
        return null;
    }

    public Question getCurrentQuestion() {
        if (currentQuestion != null) {
            return currentQuestion;
        } else {
            currentQuestion = QuestionService.getNewQuestion();
            return currentQuestion;
        }
    }


}
