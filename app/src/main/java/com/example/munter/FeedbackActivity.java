package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import core.DBHandler;
import core.Lesson;
import core.PlanEntry;

public class FeedbackActivity extends AppCompatActivity {
    Lesson lesson;
    DBHandler db;
    String lessonID = "";
    EditText feedback;
    EditText HA;
    EditText notes;
    PlanEntry[] planEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent i = getIntent();
        lessonID = i.getStringExtra("lessonID");
        db = new DBHandler(this);
        lesson = db.getLesson(Integer.parseInt(lessonID));
        planEntry = db.getPlanentry(lessonID);

        notes = (EditText) findViewById(R.id.editTextNotizen);

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        notes.setText(lesson.getComments());

        HA = (EditText) findViewById(R.id.editTextHA);

        HA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Lesson lesson1 = db.getLesson(Integer.parseInt(lessonID+1));
        HA.setText(lesson1.getHomeworks());

        feedback = (EditText) findViewById(R.id.editTextFeedback);

        feedback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        feedback.setText(lesson.getFeedback());

        Button draw = findViewById(R.id.buttonDraw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FeedbackActivity.this, drawActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
            }
        });

        TextView tv = findViewById(R.id.textAuswertung);

        String Auswertung = "<h3>Auswertung</h3>";
        for (int k = 0; k < planEntry.length; k++){
            int minutes = planEntry[k].getReallength() / 60;
            int seconds = planEntry[k].getReallength() % 60;
            String timeString = String.format("%02d:%02d", minutes, seconds);
            if (minutes < 1.2*planEntry[k].getLength() &&  minutes > 0.8*planEntry[k].getLength()) {
                Auswertung = Auswertung+"<p><u>"+planEntry[k].getTitle()+"</u> | definierte Länge: "+planEntry[k].getLength()+ ":00 min | <br/><font color=\"#009900\">tatsächlich benötige Zeit: "+timeString+"min | Zeitvorgabe gut eingehalten</font></p>";
            } else {
                Auswertung = Auswertung+"<p><u>"+planEntry[k].getTitle()+"</u> | definierte Länge: "+planEntry[k].getLength()+ ":00 min | <br/><font color=\"#b30000\">tatsächlich benötige Zeit: "+timeString+"min | größere Abweichung zur Planung</font></p>";
            }



        }
        tv.setText(HtmlCompat.fromHtml(Auswertung, HtmlCompat.FROM_HTML_MODE_LEGACY));

        //buttonLogin
        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Lesson lesson = db.getLesson(Integer.parseInt(lessonID));
                Lesson lesson1 = db.getLesson(Integer.parseInt(lessonID)+1);

                String HaText = HA.getText().toString();
                String notesText = notes.getText().toString();
                String feedbackText = feedback.getText().toString();

                lesson.setComments(notesText);
                lesson1.setHomeworks(HaText);
                lesson.setFeedback(feedbackText);

                db.updateLesson(lesson, Integer.parseInt(lessonID));
                db.updateLesson(lesson1, Integer.parseInt(lessonID)+1);

                Intent myIntent = new Intent(FeedbackActivity.this, SequenceActivity.class);
                FeedbackActivity.this.startActivity(myIntent);
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
        db = new DBHandler(getApplicationContext());
        lesson = db.getLesson(Integer.parseInt(lessonID));
        lesson.setComments(notes.getText().toString());
        lesson.setFeedback(feedback.getText().toString());
        Lesson lesson1 = db.getLesson(Integer.parseInt(lessonID+1));
        lesson1.setHomeworks(HA.getText().toString());
        db.updateLesson(lesson, lesson.getId());
        db.updateLesson(lesson1, lesson1.getId());
        Intent i = new Intent(FeedbackActivity.this, SequenceActivity.class);
        finish();
        FeedbackActivity.this.startActivity(i);
    }
}