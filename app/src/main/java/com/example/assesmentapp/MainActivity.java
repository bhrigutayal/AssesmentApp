package com.example.assesmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.StartButton);
        AppCompatEditText etName = findViewById(R.id.et_name);

        buttonStart.setOnClickListener(view -> {
            if (Objects.requireNonNull(etName.getText()).toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, QuizQuestionsActivity.class);
                intent.putExtra(Constants.USER_NAME, etName.getText().toString());
                startActivity(intent);
            }
        });
    }
}
