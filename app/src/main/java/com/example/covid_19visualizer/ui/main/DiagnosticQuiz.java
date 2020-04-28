package com.example.covid_19visualizer.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import com.example.covid_19visualizer.R;

import org.w3c.dom.Text;

import java.util.Random;

class DiagnosticQuiz {
    private final Object Bundle;

    <override> DiagnosticQuiz(Object bundle) {
       Bundle = bundle;

       Button answer1, answer2, answer3, answer4;

       TextView score, question;

       Questions mQuestions = newQuestions();

       String mAnswer;

       int mScore = 0;

       int mQuestionsLength = mQuestions.mQuestions.length;

       Random r;

       override fun; onCreate(savedInstaceState); {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.diagnostic_quiz);

           r = new Random();

           answer1 = (Button) findViewById(R.id.answer1);
           answer2 = (Button) findViewById(R.id.answer2);
           answer3 = (Button) findViewById(R.id.answer3);
           answer4 = (Button) findViewById(R.id.answer4);

           score = (TextView) findViewById(R.id.score);
           question = (TextView) findViewById(R.id.question);

           score.setText("Score: " + mScore);

           updateQuestion(r.nextInt(mQuestionsLength));

           answer1.setOnClickListener(); {
               if(answer1.getText() == mAnswer) {
                   mScore++;
                   score.setText("Score: " + mScore);
                   updateQuestion(r.nextInt(mQuestionsLength));
               } else {
                   gameOver();
               }
            };

           answer2.setOnClickListener(); {
             if(answer2.getText() == mAnswer) {
                 mScore++;
                 score.setText("Score: " + mScore);
                 updateQuestion(r.nextInt(mQuestionsLength));
             } else{
                 gameOver();
             }
            };

           answer3.setOnClickListener(); {
               if (answer3.getText() == mAnswer) {
                   mScore++;
                   score.setText("Score: " + mScore);
                   updateQuestion(r.nextInt(mQuestionsLength));
               } else{
                   gameOver();
               }
           };

           answer4.setOnClickListener(); {
               if (answer4.getText() == mAnswer) {
                   mScore++;
                   score.setText("Score: " + mScore);
                   updateQuestion(r.nextInt(mQuestionsLength));
               } else{
                   gameOver();
               }
            };
       }

       private void updateQuestions; int num; {
           question.setText(mQuestions.getQuestion(num));
           answer1.setText(mQuestions.getChoice1(num));
           answer2.setText(mQuestions.getChoice2(num));
           answer3.setText(mQuestions.getChoice3(num));
           answer4.setText(mQuestions.getChoice4(num));

           mAnswer = mQuestions.getCorrectAnswer(num);
        }

        private void gameOver() {
           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DiagnosticQuiz.this);
           alertDialogBuilder
                   .setMessage("Game Over! Your score is" + mScore + " points")
                   .setCancelable(false)
                   .setPositiveButton("New Game",
                           (dialogInterface){
                                startActivity(new Intent(applicationContext(), DiagnosticQuiz.class));
                                finish();
                           });
                   .setNegativeButton("Exit,"
                            (onClickListener) (dialogInterface) {
                                    finish()
                            });

                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
        }
    }
}
