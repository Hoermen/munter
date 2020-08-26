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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent i = getIntent();
        final String lessonID = i.getStringExtra("lessonID");
        String comments = i.getStringExtra("comments");
        final DBHandler db = new DBHandler(this);
        PlanEntry[] planEntry = db.getPlanentry(lessonID);
        String[] time = new String[planEntry.length];
        for (int k = 0; k < planEntry.length; k++) {
            time[k] = i.getStringExtra(Integer.toString(k));
        }

        final EditText notes = (EditText) findViewById(R.id.editTextNotizen);

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        notes.setText(comments);

        final EditText HA = (EditText) findViewById(R.id.editTextHA);

        HA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        final EditText feedback = (EditText) findViewById(R.id.editTextFeedback);

        feedback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        notes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(FeedbackActivity.this, drawActivity.class);
                startActivity(i);
                return false;
            }
        });
        TextView tv2 = findViewById(R.id.textNotizen);
        tv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(FeedbackActivity.this, drawActivity.class);
                startActivity(i);
                return false;
            }
        });

        TextView tv = findViewById(R.id.textAuswertung);

        String Auswertung = "<h3>Auswertung</h3>";
        for (int k = 0; k < planEntry.length; k++){
            int minutes = Integer.parseInt(time[k]) / 60;
            int seconds = Integer.parseInt(time[k]) % 60;
            String timeString = String.format("%02d:%02d", minutes, seconds);
            if (minutes < 1.2*planEntry[k].getLength() &&  minutes > 0.8*planEntry[k].getLength()) {
                Auswertung = Auswertung+"<p>Titel: "+planEntry[k].getTitle()+" | definierte Länge: "+planEntry[k].getLength()+ ":00 min | <br/><font color=\"green\">tatsächlich benötige Zeit: "+timeString+"min | Zeitvorgabe gut eingehalten</font></p>";
            } else {
                Auswertung = Auswertung+"<p>Titel: "+planEntry[k].getTitle()+" | definierte Länge: "+planEntry[k].getLength()+ ":00 min | <br/><font color=\"red\">tatsächlich benötige Zeit: "+timeString+"min | größere Abweichung zur Planung</font></p>";
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
}