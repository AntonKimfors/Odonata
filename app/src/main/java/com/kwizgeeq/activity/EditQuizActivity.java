package com.kwizgeeq.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kwizgeeq.controller.EditQuizController;

import com.kwizgeeq.databinding.ActivityEditQuizBinding;
import com.kwizgeeq.view.*;
import com.kwizgeeq.R;

import java.io.Serializable;

/*
 *  @author Marcus Olsson Lindvärn
 * revised by Anton Kimfors, Henrik Håkansson and Are Ehnberg
 */

public class EditQuizActivity extends ListActivity {
    private EditQuizController controller;
    private EditQuizView view;
    private int quizIndex;
    private final int questionListRequestCode = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Serializable quiz = bundle.getSerializable("quiz");
        quizIndex = intent.getIntExtra("quizIndex",0);


        ActivityEditQuizBinding binding;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_quiz);
        view = new EditQuizView(EditQuestionActivity.class, quiz, getListView(), this, this,
                questionListRequestCode);


        controller = new EditQuizController(view, quiz);
        binding.setController(controller);
        view.addObserver(controller);
    }

    @Override
    public void onBackPressed() {
        controller.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == questionListRequestCode && resultCode == RESULT_OK){
            if(data.getSerializableExtra("questions")!=null){
                Serializable questions = data.getSerializableExtra("questions");
                controller.setQuestionList(questions);
            }
        }
    }

}