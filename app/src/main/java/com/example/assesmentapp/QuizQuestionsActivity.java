package com.example.assesmentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;

public class QuizQuestionsActivity extends AppCompatActivity implements View.OnClickListener {

    private int mCurrentPosition = 1;
    private ArrayList<Question> mQuestionsList;
    private int mSelectedOptionPosition = 0;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private int mCorrectAnswers = 0;
    private TextView tvQuestion;
    private ImageView ivImage;
    private String mUserName;
    private TextView tvOptionOne;
    private TextView tvOptionTwo;
    private TextView tvOptionThree;
    private TextView tvOptionFour;
    private Button btnSubmit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        mUserName = getIntent().getStringExtra(Constants.USER_NAME);
        progressBar = findViewById(R.id.progressbar);
        tvProgress = findViewById(R.id.tv_progress);
        tvQuestion = findViewById(R.id.tv_question);
        ivImage = findViewById(R.id.iv_image);
        tvOptionOne = findViewById(R.id.tv_option_one);
        tvOptionTwo = findViewById(R.id.tv_option_two);
        tvOptionThree = findViewById(R.id.tv_option_three);
        tvOptionFour = findViewById(R.id.tv_option_four);

        mQuestionsList = Constants.getQuestions();
        btnSubmit = findViewById(R.id.btn_submit);

        setQuestion();

        tvOptionOne.setOnClickListener(this);
        tvOptionTwo.setOnClickListener(this);
        tvOptionThree.setOnClickListener(this);
        tvOptionFour.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void setQuestion() {
        Question question = mQuestionsList.get(mCurrentPosition - 1);
        defaultOptionsView();
        progressBar.setMax(mQuestionsList.size());

        if (mCurrentPosition == mQuestionsList.size()) {
            btnSubmit.setText("FINISH");
        } else {
            btnSubmit.setText("SUBMIT");
        }

        progressBar.setProgress(mCurrentPosition);
        tvProgress.setText(mCurrentPosition + "/" + progressBar.getMax());

        ivImage.setImageResource(question.getImage());
        tvQuestion.setText(question.getQuestion());
        tvOptionOne.setText(question.getOptionOne());
        tvOptionTwo.setText(question.getOptionTwo());
        tvOptionThree.setText(question.getOptionThree());
        tvOptionFour.setText(question.getOptionFour());
    }

    private void defaultOptionsView() {
        ArrayList<TextView> options = new ArrayList<>();
        options.add(tvOptionOne);
        options.add(tvOptionTwo);
        options.add(tvOptionThree);
        options.add(tvOptionFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.default_option_border_bg));
        }
    }

    private void selectedOptionView(TextView tv, int selectedOptionNum) {
        defaultOptionsView();
        mSelectedOptionPosition = selectedOptionNum;
        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.selected_option_border));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_option_one) {
            selectedOptionView(tvOptionOne, 1);
        } else if (id == R.id.tv_option_two) {
            selectedOptionView(tvOptionTwo, 2);
        } else if (id == R.id.tv_option_three) {
            selectedOptionView(tvOptionThree, 3);
        } else if (id == R.id.tv_option_four) {
            selectedOptionView(tvOptionFour, 4);
        } else if (id == R.id.btn_submit) {
            if (mSelectedOptionPosition == 0) {
                mCurrentPosition++;
                if (mCurrentPosition <= mQuestionsList.size()) {
                    setQuestion();
                } else {
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra(Constants.USER_NAME, mUserName);
                    intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers);
                    intent.putExtra(Constants.TOTAL_QUESTION, mQuestionsList.size());
                    startActivity(intent);
                    finish();
                }
            } else {
                Question question = mQuestionsList.get(mCurrentPosition - 1);
                if (question.getCorrectAnswer() != mSelectedOptionPosition) {
                    answerView(mSelectedOptionPosition, R.drawable.wrong_option_border);
                } else {
                    mCorrectAnswers++;
                }
                answerView(question.getCorrectAnswer(), R.drawable.correct_option_border);
                if (mCurrentPosition == mQuestionsList.size()) {
                    btnSubmit.setText("FINISH");
                } else {
                    btnSubmit.setText("GO TO NEXT QUESTION");
                }
                mSelectedOptionPosition = 0;
            }
        }
    }


    private void answerView(int answer, int drawableView) {
        switch (answer) {
            case 1:
                tvOptionOne.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 2:
                tvOptionTwo.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 3:
                tvOptionThree.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 4:
                tvOptionFour.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
        }
    }
}
