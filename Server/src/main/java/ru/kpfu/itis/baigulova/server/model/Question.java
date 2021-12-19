package ru.kpfu.itis.baigulova.server.model;

public class Question {
    private String title;
    private String questionText;
    private String[] answers;
    private int rightAnswer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public Question(String title, String text, String[] answers, int rightAnswer) {
        this.title = title;
        this.questionText = text;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public Question(String[] args){
        this.title=args[0];
        this.questionText=args[1];
        this.answers=new String[]{
                args[2],args[3],args[4]
        };
        this.rightAnswer=Integer.parseInt(args[5]);
    }

    public boolean checkAnswer(int answer) {
        return answer==rightAnswer;
    }
}
