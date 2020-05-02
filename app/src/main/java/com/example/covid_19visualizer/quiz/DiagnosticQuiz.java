package com.example.covid_19visualizer.quiz;

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
                    if (Arrays.asList("Fever, chills, or sweating", "Moderate to severe asthma or chronic lung disease").contains( (String) answer1.getText())) {
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 5;
                    }
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2.isChecked()) {
                    if (answer2.getText() == "I've had close contact with someone who has COVID-19") {
                        mScore = mScore + 5;
                    }
                    if (Arrays.asList("Difficulty breathing (not severe)", "Cancer treatment or medicines causing immune suppression").contains( (String) answer2.getText())) {
                        mScore = mScore + 1;
                    }
                    if (Arrays.asList("Between 18 and 64", "I have visited an area where COVID-19 is widespread").contains( (String) answer2.getText())) {
                        mScore = mScore + 3;
                    } else {
                        mScore = mScore + 0;
                    }
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3.isChecked()) {
                    if (answer3.getText() == "65 or older") {
                        mScore = mScore + 5;
                    }
                    if (Arrays.asList("New or worsening cough", "Inherited immune system deficiencies").contains( (String) answer3.getText())) {
                        mScore = mScore + 1;
                    }
                    if (Arrays.asList("I don't know", "I've been near someone who has COVID-19").contains( (String) answer3.getText())) {
                        mScore = mScore + 3;
                    } else {
                        mScore = mScore + 0;
                    }
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4.isChecked()) {
                    if (Arrays.asList("Sore throat", "Serious heart conditions, such as heart failure or prior heart attack").contains((String) answer4.getText())) {
                        mScore = mScore + 1;
                    }
                }
            }
        });

        answer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer5.isChecked()) {
                    if (answer5.getText() == "I don't know") {
                        mScore = mScore +3;
                    }
                    if (Arrays.asList("Aching throughout the body", "Diabetes with complications").contains( (String) answer5.getText())){
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 0;
                    }
                }
            }
        });

        answer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer6.isChecked()) {
                    if (Arrays.asList("Vomiting or diarrhea", "Kidney failure that needs dialysis").contains( (String) answer6.getText())){
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 0;
                    }
                }
            }
        });

        answer7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer7.isChecked()) {
                    if (answer7.getText() == "Cirrhosis of the liver") {
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 0;
                    }

                }
            }
        });

        answer8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer8.isChecked()) {
                    if (answer8.getText() == "Diseases or conditions that make it harder to cough") {
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 0;
                    }
                }
            }
        });


        answer9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer9.isChecked()) {
                    if (answer9.getText() == "Extreme obesity") {
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 0;
                    }
                }
            }
        });


        answer10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer10.isChecked()) {
                    if (answer10.getText() == "Pregnancy") {
                        mScore = mScore + 1;
                    } else {
                        mScore = mScore + 0;
                    }
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
