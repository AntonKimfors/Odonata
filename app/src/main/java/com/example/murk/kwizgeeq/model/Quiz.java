package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Quiz implements Iterable{
    protected ArrayList<Question> questions;
    private int listColor;
    private String name;


    public Quiz(String name,int listColor) {
        questions = new ArrayList<>();
        this.name = name;
        this.listColor = listColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getListColor() {
        return listColor;
    }

    public void setListColor(int listColor) {
        this.listColor = listColor;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void removeQuestion(Question question){
        questions.remove(question);
    }

    public int getSize(){
        return questions.size();
    }

    public Question getQuestion(int questionIndex){
        return questions.get(questionIndex);
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    @Override
    public Iterator iterator() {
        return questions.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Quiz name: ").append(name).append(System.lineSeparator());
        sb.append("Color: ").append(listColor).append(System.lineSeparator());
        for(Question q:questions){
            sb.append(q.toString());
        }

        return sb.toString();
    }
}
