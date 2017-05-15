package com.example.murk.kwizgeeq.model;

/**
 * Created by Henrik on 04/04/2017.
 */

public class Answer<T>  {
    private final boolean isCorrect;
    private final T data;
    private final AnswerType answerType;

    public T getData() {
        return data;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public AnswerType getAnswerType(){
        return answerType;
    }

    public Answer(boolean isCorrect, T data, AnswerType answerType) {
        this.isCorrect = isCorrect;
        this.data = data;
        this.answerType = answerType;
    }

    /**
     * Objects are equal if the data data is equal
     * @param obj
     * @return true is data
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(obj instanceof Answer){
            Answer<T> o = (Answer<T>) obj;
            return data.equals(o.data);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString(){
        Boolean pointerBoolean = (Boolean)isCorrect;
        return data.toString() + " - " + pointerBoolean.toString();
    }
}
