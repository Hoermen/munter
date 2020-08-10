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
import core.Resource;
import core.Sequence;

public class Durchfuehrung extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durchfuehrung);

        db = new DBHandler(getApplicationContext());

        EditText notes = (EditText) findViewById(R.id.editTextNotes);

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Intent i = getIntent();
        final String lessonID = i.getStringExtra("lessonID");

        final TextView tv = (TextView) findViewById(R.id.title);
        final TextView current = (TextView) findViewById(R.id.current);
        final TextView previous = (TextView) findViewById(R.id.previous);
        final TextView next = (TextView) findViewById(R.id.next);

        final Lesson neu = db.getLesson(Integer.parseInt(lessonID));
        final Sequence[] sequence = db.getSequence();
        final PlanEntry[] planEntry = db.getPlanentry("1");
        final Resource[] resource = db.getResource();

        String html = "<h2>"+planEntry[0].getTitle()+"</h2><p>"+planEntry[0].getComments()+"</p";
        current.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        tv.setText(neu.getTitle());
        current.setText(sequence[0].getComments());
        previous.setText(planEntry[0].getTitle());
        next.setText(resource[1].getTextContent());

        //buttonStart
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(Durchfuehrung.this, FeedbackActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}