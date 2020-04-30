package com.example.covid_19visualizer.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19visualizer.R;

import java.util.Random;

public class DiagnosticQuiz extends AppCompatActivity {
    Button next_button;

    CheckBox answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10, answer11;

    TextView question;

    private Questions mQuestions = new Questions();

    private String mAnswer;
    private int mQuestionsLength = mQuestions.mQuestions.length;

    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnostic_quiz);

        r = new Random();

        answer1 = (CheckBox) findViewById(R.id.answer1);
        answer2 = (CheckBox) findViewById(R.id.answer2);
        answer3 = (CheckBox) findViewById(R.id.answer3);
        answer4 = (CheckBox) findViewById(R.id.answer4);
        answer5 = (CheckBox) findViewById(R.id.answer5);
        answer6 = (CheckBox) findViewById(R.id.answer6);
        answer7 = (CheckBox) findViewById(R.id.answer7);
        answer8 = (CheckBox) findViewById(R.id.answer8);
        answer9 = (CheckBox) findViewById(R.id.answer9);
        answer10 = (CheckBox) findViewById(R.id.answer10);
        answer11 = (CheckBox) findViewById(R.id.answer11);
        next_button = (Button) findViewById(R.id.next_button);


        question = (TextView) findViewById(R.id.question);


        updateQuestion(r.nextInt(mQuestionsLength));

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1.isChecked()) {

                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2.isChecked()) {

                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3.isChecked()) {

                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4.isChecked()) {

                }
            }
        });

        answer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer5.isChecked()) {

                }
            }
        });

        answer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer6.isChecked()) {

                }
            }
        });

        answer7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer7.isChecked()) {

                }
            }
        });

        answer8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer8.isChecked()) {

                }
            }
        });


        answer9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer9.isChecked()) {

                }
            }
        });


        answer10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer10.isChecked()) {

                }
            }
        });

        answer11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer11.isChecked()) {

                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (mQuestions.getQuestion(mQuestionsLength) == mQuestions.getQuestion(9)) {
                  //  gameOver();
               // } else {
                    if (next_button.getText() == mAnswer) {
                        toggle();
                        updateQuestion(r.nextInt(mQuestionsLength));
                    }
                //}
            }
        });
    }

    private void updateQuestion(int num) {
        question.setText(mQuestions.getQuestion(num));
        answer1.setText(mQuestions.getChoice1(num));
        answer2.setText(mQuestions.getChoice2(num));
        answer3.setText(mQuestions.getChoice3(num));
        answer4.setText(mQuestions.getChoice4(num));
        answer5.setText(mQuestions.getChoice5(num));
        answer6.setText(mQuestions.getChoice6(num));
        answer7.setText(mQuestions.getChoice7(num));
        answer8.setText(mQuestions.getChoice8(num));
        answer9.setText(mQuestions.getChoice9(num));
        answer10.setText(mQuestions.getChoice10(num));
        answer11.setText(mQuestions.getChoice11(num));
        next_button.setText(mQuestions.getChoice12(num));

        mAnswer = mQuestions.getCorrectAnswer(num);

    }

    private void toggle() {
        if (answer1.isChecked()) {
            answer1.toggle();
        }

        if (answer2.isChecked()) {
            answer2.toggle();
        }

        if (answer3.isChecked()) {
            answer3.toggle();
        }

        if (answer4.isChecked()) {
            answer4.toggle();
        }

        if (answer5.isChecked()) {
            answer5.toggle();
        }

        if (answer6.isChecked()) {
            answer6.toggle();
        }

        if (answer7.isChecked()) {
            answer7.toggle();
        }

        if (answer8.isChecked()) {
            answer8.toggle();
        }

        if (answer9.isChecked()) {
            answer9.toggle();
        }

        if (answer10.isChecked()) {
            answer10.toggle();
        }

        if (answer11.isChecked()) {
            answer11.toggle();
        }
    }

    private void gameOver() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DiagnosticQuiz.this);
        alertDialogBuilder
                .setMessage("Your recommended steps are: \n 1. Isolate from others \n 2. Rest and Take care \n 3. Talk to someone about testing \n 4. Monitor Symptoms")
                .setCancelable(false)
                .setPositiveButton("RETAKE QUIZ",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),DiagnosticQuiz.class));
                                finish();
                            }
                })
                .setNegativeButton("EXIT",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        }
    }
