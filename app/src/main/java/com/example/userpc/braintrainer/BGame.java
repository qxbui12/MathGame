package com.example.userpc.braintrainer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class BGame extends AppCompatActivity {

    private Random rand;
    private Button tLeftBtn;
    private Button tRightBtn;
    private Button bLeftBtn;
    private Button bRightBtn;
    private TextView questionTxt;
    private TextView answerTxt;
    private TextView timeTxt;
    private TextView scoreTxt;
    private int answer;
    private int correctButton;
    private boolean startRepeat;
    private int goodAnswer;
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgame);

        tLeftBtn = (Button) findViewById(R.id.topLeftButton);
        tRightBtn = (Button) findViewById(R.id.topRightButton);
        bLeftBtn = (Button) findViewById(R.id.bottomLeftButton);
        bRightBtn = (Button) findViewById(R.id.bottomRightButton);

        questionTxt = (TextView) findViewById(R.id.questionTextView);
        answerTxt = (TextView) findViewById(R.id.answerCheckTextView);
        timeTxt = (TextView) findViewById(R.id.remainTimeTextView);
        scoreTxt = (TextView) findViewById(R.id.scoreTrackTextView);

        Intent mainGame = getIntent();

        int defaultTimeInSec = 30;
        goodAnswer = 0;
        totalQuestions = 0;
        startRepeat = true;

        CountDownTimer timer = new CountDownTimer(defaultTimeInSec*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeTxt.setText(String.valueOf(millisUntilFinished / 1000));

                while(startRepeat){
                    startRepeat = false;
                    rand = new Random();
                    int num1 = rand.nextInt(100) + 1;
                    int num2 = rand.nextInt(100)+ 1;
                    if(num1 < num2){
                        int temp = num1;
                        num1 = num2;
                        num2 = temp;
                    }
                    int opChoice = rand.nextInt(2);
                    correctButton = rand.nextInt(4);

                    String[] op = {"+","-"};

                    answer = compute(num1, num2, opChoice);

                    questionTxt.setText(String.valueOf(num1)+" "+op[opChoice]+" "+String.valueOf(num2));

                    assignAnsToBtn();
                }

            }

            public void onFinish() {
                timeTxt.setText("0");
                answerTxt.setText("Time's up!");
                tLeftBtn.setEnabled(false);
                tRightBtn.setEnabled(false);
                bLeftBtn.setEnabled(false);
                bRightBtn.setEnabled(false);
            }
        }.start();







    }

    public void checkAnswer(View view){
        if((view.getId() == R.id.topLeftButton && correctButton == 0)||
                (view.getId() == R.id.topRightButton && correctButton == 1)||
                (view.getId() == R.id.bottomLeftButton && correctButton == 2)||
                (view.getId() == R.id.bottomRightButton && correctButton == 3)){
            answerTxt.setText("Correct!!");
            scoreTxt.setText(String.valueOf(++goodAnswer)+"/"+String.valueOf(++totalQuestions));
        }
        else{
            answerTxt.setText("Wrong!!");
            scoreTxt.setText(String.valueOf(goodAnswer)+"/"+String.valueOf(++totalQuestions));
        }
        startRepeat = true;
    }

    private int compute(int num1, int num2, int op){
        if(op == 0){
            return num1+num2;
        }
        return num1-num2;
    }

    private void assignAnsToBtn(){
        Random rand = new Random();
        int ghostValue = rand.nextInt(202);
        int ghostValue2 = rand.nextInt(202);
        int ghostValue3 = rand.nextInt(202);
        while(ghostValue == answer || ghostValue2 == answer || ghostValue3 == answer){
            ghostValue = rand.nextInt(202);
            ghostValue2 = rand.nextInt(202);
            ghostValue3 = rand.nextInt(202);
        }

        if(correctButton == 0){
            tLeftBtn.setText(String.valueOf(answer));
            tRightBtn.setText(String.valueOf(ghostValue));
            bLeftBtn.setText(String.valueOf(ghostValue2));
            bRightBtn.setText(String.valueOf(ghostValue3));
        }
        else if(correctButton == 1){
            tLeftBtn.setText(String.valueOf(ghostValue));
            tRightBtn.setText(String.valueOf(answer));
            bLeftBtn.setText(String.valueOf(ghostValue2));
            bRightBtn.setText(String.valueOf(ghostValue3));
        }
        else if(correctButton == 2){
            tLeftBtn.setText(String.valueOf(ghostValue));
            tRightBtn.setText(String.valueOf(ghostValue2));
            bLeftBtn.setText(String.valueOf(answer));
            bRightBtn.setText(String.valueOf(ghostValue3));
        }
        else{
            tLeftBtn.setText(String.valueOf(ghostValue));
            tRightBtn.setText(String.valueOf(ghostValue2));
            bLeftBtn.setText(String.valueOf(ghostValue3));
            bRightBtn.setText(String.valueOf(answer));
        }
    }


}
