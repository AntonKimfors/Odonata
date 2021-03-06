

package com.kwizgeeq.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;


import com.kwizgeeq.model.KwizGeeQ;
import com.kwizgeeq.model.Quiz;

import com.kwizgeeq.view.QuizListView;

import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;



/**
 * Created by akimfors on 2017-05-05.
 *
 * @author Anton Kimfors
 * revised by Henrik Håkansson, Are Ehnberg and Marcus Olsson Lindvärn
 */

public class QuizListController implements Observer{

    private final AdapterView.OnItemLongClickListener itemLongClickListener;
    private final View.OnClickListener createQuizListener;
    private final ColorPickerSwatch.OnColorSelectedListener colorPickerListener;
    private final View.OnClickListener mCancelListener;
    private final View.OnClickListener mPickColorListener;

    private QuizListView quizListView;
    private KwizGeeQ kwizGeeQ;
    private List<Quiz> quizList;

    public QuizListController(QuizListView view, Activity currentActivity) {
        this.kwizGeeQ = new KwizGeeQ(currentActivity);
        this.quizListView = view;
        quizList = kwizGeeQ.getQuizList();
        quizListView.setQuestions((ArrayList)quizList);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int quizIndex, long id) {
                try {
                    quizList.get(quizIndex).getQuestion(0);
                    quizListView.startQuestioneer(quizList.get(quizIndex), quizIndex);
                } catch (IndexOutOfBoundsException e){
                    quizListView.showObligatoryCloseQuizDialog("Cannot play an empty quiz.");
                }
            }
        };

        view.setOnListItemClickedListener(onItemClickListener);

        itemLongClickListener = new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int quizIndex, long id) {
                quizListView.setAlertDialogPositiveListener(getPositiveListener(quizIndex));
                quizListView.setAlertDialogNegativeListener(getNegativeListener(quizIndex));
                quizListView.setAlertDialogNeutralListener(getDismissListener());
                quizListView.showAlertDialog();

                return true;
            }

        };
        view.setOnItemLongClickListener(itemLongClickListener);

        createQuizListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quizTitle = quizListView.getQuizName();
                int color = quizListView.getmSelectedColor();
                Quiz newQuiz = new Quiz(quizTitle, color);
                quizListView.createNewQuiz(newQuiz);
                quizListView.dismissCreationDialog();
            }
        };
        quizListView.setmCreateQuizOnClickListener(createQuizListener);

        colorPickerListener = new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                quizListView.setmSelectedColor(color);
                quizListView.updateColorBackground();
            }

        };
        quizListView.setColorPickerListener(colorPickerListener);

        mCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizListView.dismissCreationDialog();
            }
        };
        quizListView.setmCancelOnClickListener(mCancelListener);

        mPickColorListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                quizListView.showColorDialog();
            }
        };
        quizListView.setmPickColorListener(mPickColorListener);


    };

    private DialogInterface.OnClickListener getPositiveListener(final int quizIndex){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizListView.editQuiz(quizList.get(quizIndex));
                quizListView.dismissAlertDialog();
            }
        };
    }

    private DialogInterface.OnClickListener getNegativeListener(final int quizIndex){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kwizGeeQ.removeQuiz(quizIndex);
            }
        };
    }

    private DialogInterface.OnClickListener getDismissListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizListView.dismissAlertDialog();
            }
        };
    }

    public void onClickAction(View view) {
        this.quizListView.fabPressed();
    }

    public void globalStatisticsButtonPressed(View view){
        this.quizListView.openGlobalStatistics(kwizGeeQ.getGlobalStatistics());
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    public void addQuiz(Serializable quiz) {
        if(quiz instanceof Quiz){
            kwizGeeQ.addQuiz((Quiz)quiz);
        }
    }

    public void replaceQuiz(Serializable quiz, int quizIndex) {
        if(quiz instanceof Quiz){
            kwizGeeQ.replaceQuiz(quizIndex,(Quiz)quiz);
        }
    }

    public void updateStatistics(Serializable quiz){
        if (quiz instanceof Quiz){
            ((Quiz)quiz).updateBestStatistics();
            kwizGeeQ.updateGlobalStatistics((Quiz)quiz);
        }
    }
}

