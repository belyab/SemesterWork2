package ru.kpfu.itis.baigulova.server.model;

public class Answer {
    private boolean isRight;
    private long startTime;
    private long endTime;

    public Answer(long startTime) {
        this.startTime = startTime;
    }

    //окончание игры
}
