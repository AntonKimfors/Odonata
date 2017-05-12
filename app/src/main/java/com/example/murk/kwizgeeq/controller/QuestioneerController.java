package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Statistics;
import com.example.murk.kwizgeeq.view.QuestioneerView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerController implements Controller, Observer{

    private QuestioneerView view;
    private KwizGeeQ model;


    private int quizIndex;
    private int questionIndex;

    public QuestioneerController(QuestioneerView view, int quizIndex) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.quizIndex = quizIndex;
        this.questionIndex = 0;
    }

    public void onCreate() {
        view.updateQuizRelatedItems(quizIndex);
        view.updateQuestioneer(quizIndex, questionIndex);
        model.getCurrentQuizStatistics().startTimer();
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        //TODO Switch to statistics activity
    }

    public void answerSelected(View view){
        model.getCurrentQuizStatistics().incQuestionCount();
        if(((Answer)view.getTag()).isCorrect()){
            model.getCurrentQuizStatistics().incAnswerCorrectCount();
            this.view.flashCorrectAnswer(view);
        } else{
            model.getCurrentQuizStatistics().incAnswerIncorrectCount();
            this.view.flashIncorrectAnswer(view);
        }
    }

    public void finishQuestion(){
        if(questionIndex + 1 == model.getQuiz(quizIndex).getQuestions().size()) {
            model.getCurrentQuizStatistics().incQuizCount();
            model.getCurrentQuizStatistics().stopTimer();
            model.endQuiz();
            view.closeQuestioneer();
        }
        else {
            questionIndex++;
            view.updateQuestioneer(quizIndex, questionIndex);
        }
    }

    public void update(Observable o, Object arg) {
        if(arg == "question done"){
            finishQuestion();
        }
    }
}
