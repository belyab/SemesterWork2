package ru.kpfu.itis.baigulova.server.model;

public class Question {
    private String title;
    private String questionText;
    private String[] answers;
    private int rightAnswer;

    public Question(String title, String questionText, String[] answers, int rightAnswer) {
        this.title = title;
        this.questionText = questionText;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public Question(String[] args){
        this.title=args[0];
        this.questionText=args[1];
        this.answers=new String[]{
                args[2],args[3],args[4],args[5]
        };
        this.rightAnswer=Integer.parseInt(args[6]);
    }

    public boolean checkAnswer(int answer) {
        return answer==rightAnswer;
    }
}