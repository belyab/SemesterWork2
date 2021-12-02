package ru.kpfu.itis.baigulova.server.service;

import ru.kpfu.itis.baigulova.server.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

public class QuestionService {
    private static LinkedList<Question> questions = new LinkedList<>();

    public static void setQuestions(String path) throws IOException {
        InputStream input = QuestionService.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (reader.ready()) {
            String line = reader.readLine();
            String[] attributes = line.split("::");
            questions.add(new Question(attributes));
        }
    }

    public static Question getNewQuestion() {
        Random random = new Random(System.currentTimeMillis());
        return questions.get(random.nextInt((questions.size() - 1) + 1));
    }
}
