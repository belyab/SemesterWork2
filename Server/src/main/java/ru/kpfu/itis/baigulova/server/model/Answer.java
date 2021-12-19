package ru.kpfu.itis.baigulova.server.model;

public class Answer {
    private boolean isRight;
    private long startTime;
    private long endTime;
    public boolean isGameEnd = false;

    public Answer(long startTime) {
        this.startTime = startTime;
    }

    public void gameEnd() {
        this.endTime = System.currentTimeMillis() - startTime;
        this.isGameEnd = Boolean.TRUE;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isGameEnd() {
        return isGameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        isGameEnd = gameEnd;
    }
}
