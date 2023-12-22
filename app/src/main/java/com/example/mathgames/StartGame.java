package com.example.mathgames;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class StartGame extends AppCompatActivity {

    int op1, op2, correctAnswer, incorrectAnswer;
    TextView tvTimer, tvPoints, tvSum, tvResult;
    Button btn0, btn1, btn2, btn3;
    CountDownTimer countDownTimer;
    long millisUntilFinished;
    int points;
    int numberOfQuestions;
    Random random;
    int[] btnIds;
    int correcrAnswerPosition;
    ArrayList<Integer> incorrectAnswers;
    String[] operatorArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);

        op1 = 0;
        op2 = 0;
        correctAnswer = 0;
        incorrectAnswer = 0;
        tvTimer = findViewById(R.id.tvTimer);
        tvPoints = findViewById(R.id.tvPoints);
        tvSum = findViewById(R.id.tvSum);
        tvResult = findViewById(R.id.tvResult);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        millisUntilFinished = 30100;
        points = 0;
        numberOfQuestions = 0;

        random = new Random();
        btnIds = new int[]{R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3};
        correcrAnswerPosition = 0;
        incorrectAnswers = new ArrayList<>();
        operatorArray = new String[]{"+", "-", "*", "÷"};
        startGame();


    }

    private void startGame() {

        tvTimer.setText("" + (millisUntilFinished / 1000) + "s");
        tvPoints.setText("" + points + "/" + numberOfQuestions);
        generateQuestions();

        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                tvTimer.setText("" + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                btn0.setClickable(false);
                btn1.setClickable(false);
                btn2.setClickable(false);
                btn3.setClickable(false);
                Intent intent = new Intent(StartGame.this, GameOver.class);
                intent.putExtra("points", points);
                startActivity(intent);
                
                // x button close app
                finish();






            }
        }.start();
    }

    private void generateQuestions() {
        numberOfQuestions++;
        op1 = random.nextInt(10);

        // division by 0... undefined
        op2 =  1 + random.nextInt(9);
        String selectedOperator = operatorArray[random.nextInt(4)];
        correctAnswer = getAnswer(selectedOperator);
        tvSum.setText(op1 + " " + selectedOperator + " " + op2 + " = ");
        correcrAnswerPosition = random.nextInt(4);
        ((Button)findViewById(btnIds[correcrAnswerPosition])).setText(""+correctAnswer);

        while(true)
        {
            if(incorrectAnswers.size() > 3)
            {
                break;
            }
            op1 = random.nextInt(10);
            op2 = 1 + random.nextInt(9);
            selectedOperator = operatorArray[random.nextInt(4)];
            incorrectAnswer = getAnswer(selectedOperator);
            if(incorrectAnswer == correctAnswer)
            {
                continue;
            }
            incorrectAnswers.add(incorrectAnswer);
        }
        for(int i =0; i<3; i++)
        {
            if(i == correcrAnswerPosition)
            {
                continue;
            }
            ((Button)findViewById(btnIds[i])).setText(""+incorrectAnswers.get(i));

        }
        incorrectAnswers.clear();

    }



    private int getAnswer(String selectedOperator) {
        int answer = 0;
        switch (selectedOperator)
        {
            case "+":
                answer = op1 + op2;
                break;

            case "-":
                answer = op1 - op2;
                break;

            case "*":
                answer = op1 * op2;
                break;

            case "÷":
                answer = op1 / op2;
                break;
        }

        return answer;
    }

    public void chooseAnswer(View view) {
        int answer = Integer.parseInt(((Button) view).getText().toString());
        if(answer == correctAnswer)
        {
            points++;
            tvResult.setText("Correct!");
        }
        else
        {
            tvResult.setText("WRONG!!!");
        }
        tvPoints.setText(points + "/" + numberOfQuestions);
        generateQuestions();

    }
}
