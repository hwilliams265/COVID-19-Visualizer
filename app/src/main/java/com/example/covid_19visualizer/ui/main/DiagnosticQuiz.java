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

import java.lang.String;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class DiagnosticQuiz extends AppCompatActivity {
    Button next_button;

    CheckBox answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10, answer11;

    TextView question;

    private Questions mQuestions = new Questions();

    private double mScore = 0;
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
        final List<Integer> QuestionNumbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        //String currentQuestion = (String) question.getText();
        //int QuestionNumber = Arrays.asList(mQuestions.mQuestions).indexOf(currentQuestion);

        updateQuestion(0);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1.isChecked()) {
                    mScore = mScore + 4.11;
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2.isChecked()) {
                    mScore = mScore + 2.11;
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3.isChecked()) {
                    mScore = mScore + 2.17;
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4.isChecked()) {
                    mScore = mScore + 0.5;
                }
            }
        });

        answer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer5.isChecked()) {
                    mScore = mScore + 1.67;
                }
            }
        });

        answer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer6.isChecked()) {
                    mScore = mScore + 1;
                }
            }
        });

        answer7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer7.isChecked()) {
                    mScore = mScore + 1;
                }
            }
        });

        answer8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer8.isChecked()) {
                    mScore = mScore + 1;
                }
            }
        });


        answer9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer9.isChecked()) {
                    mScore = mScore + 1;
                }
            }
        });


        answer10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer10.isChecked()) {
                    mScore = mScore + 1;
                }
            }
        });

        answer11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer11.isChecked()) {
                    mScore = mScore + 0;
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next_button.getText() == mAnswer) {
                    toggle();
                    String currentQuestion = (String) question.getText();
                    int QuestionNumber = Arrays.asList(mQuestions.mQuestions).indexOf(currentQuestion);
                    int n = QuestionNumber + 1;
                    updateQuestion(n);
                }

                if (question.getText() == " ") {
                    gameOver();
                }
            }
        });
    }

    private void updateQuestion(int i) {

        question.setText(mQuestions.getQuestion(i));

        answer1.setText(mQuestions.getChoice1(i));
            int viewMode = 0;
            if (answer1.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer1.setVisibility(viewMode);

        answer2.setText(mQuestions.getChoice2(i));
            if (answer2.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer2.setVisibility(viewMode);

        answer3.setText(mQuestions.getChoice3(i));
            if (answer3.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer3.setVisibility(viewMode);

        answer4.setText(mQuestions.getChoice4(i));
            if (answer4.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer4.setVisibility(viewMode);

        answer5.setText(mQuestions.getChoice5(i));
            if (answer5.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer5.setVisibility(viewMode);

        answer6.setText(mQuestions.getChoice6(i));
            if (answer6.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer6.setVisibility(viewMode);

        answer7.setText(mQuestions.getChoice7(i));
            if (answer7.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer7.setVisibility(viewMode);

        answer8.setText(mQuestions.getChoice8(i));
            if (answer8.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer8.setVisibility(viewMode);

        answer9.setText(mQuestions.getChoice9(i));
            if (answer9.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer9.setVisibility(viewMode);

        answer10.setText(mQuestions.getChoice10(i));
            if (answer10.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer10.setVisibility(viewMode);

        answer11.setText(mQuestions.getChoice11(i));
            if (answer11.getText() == " ") {
                viewMode = View.GONE;
            } else {
                viewMode = View.VISIBLE;
            }

            answer11.setVisibility(viewMode);

        next_button.setText(mQuestions.getChoice12(i));

        mAnswer = mQuestions.getCorrectAnswer(i);

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
                if (mScore >= 15) {
                    alertDialogBuilder
                    .setMessage("Your recommended steps are: \n 1. Isolate from others \n 2. Rest and Take care \n 3. Talk to someone about testing \n 4. Monitor Symptoms")
                            .setCancelable(false);
                } else {
                    if (mScore < 15) {
                        alertDialogBuilder
                                .setMessage("Your recommended steps are: \n 1. Maintain Social Distance")
                                    .setCancelable(false);
                    }
                }

                alertDialogBuilder
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
