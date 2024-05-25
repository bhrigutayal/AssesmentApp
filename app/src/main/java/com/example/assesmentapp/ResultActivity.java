package com.example.assesmentapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvScore = findViewById(R.id.tv_score);
        Button btnFinish = findViewById(R.id.btn_finish);

        tvName.setText(getIntent().getStringExtra(Constants.USER_NAME));
        int correctAnswers = getIntent().getIntExtra(Constants.CORRECT_ANSWERS, 0);
        int totalQuestions = getIntent().getIntExtra(Constants.TOTAL_QUESTION, 0);

        tvScore.setText("Your Score is " + correctAnswers + " out of " + totalQuestions);

        btnFinish.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));


    }


}
