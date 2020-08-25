package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import core.DBHandler;
import core.DrawView;
import core.Lesson;
import core.PlanEntry;
import core.Resource;

public class LessonActivity extends AppCompatActivity {
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);
        Intent i = getIntent();
        final String lessonID = i.getStringExtra("lessonID");

        DBHandler db = new DBHandler(getApplicationContext());
        Lesson lesson = db.getLesson(Integer.parseInt(lessonID));
        Resource[] resource = db.getResource(Integer.parseInt(lessonID));
        String mat = "<h4>Materialien:</h4>";
        for (int j = 0; j < resource.length; j++) {
            mat=mat+"<p><font color=\"black\"><a href=\"https://google.de\">"+resource[j].getTitle()+" ("+resource[j].getFilename()+")</a></font></p>";
        }

        final TextView lessonText = (TextView) findViewById(R.id.LessonInfo);
        String html = "<h2><u>"+lesson.getTitle()+"</u></h2><p>Beschreibung: "+lesson.getBeschreibung()+"</p>";
        lessonText.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final TextView HAText = (TextView) findViewById(R.id.HAInfo);
        String html2 = "<h4>Hausaufgaben:</h4><p>"+lesson.getHomeworks()+"</p>";
        HAText.setText(HtmlCompat.fromHtml(html2, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final TextView materialien = findViewById(R.id.LessonMaterialien);
        materialien.setText(HtmlCompat.fromHtml(mat, HtmlCompat.FROM_HTML_MODE_LEGACY));
        materialien.setMovementMethod(LinkMovementMethod.getInstance());

        final TextView notesText = findViewById(R.id.textNotizen);
        String html3 = "<h4>Notizen:</h4>";
        notesText.setText(HtmlCompat.fromHtml(html3, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final EditText checkliste = (EditText) findViewById(R.id.Checkliste);

        checkliste.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        checkliste.setText(lesson.getComments());
        checkliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView = new DrawView(LessonActivity.this);
                setContentView(drawView);
                drawView.requestFocus();
            }
        });

        //buttonStart
        Button ButtonStart = (Button) findViewById(R.id.ButtonStart);
        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(LessonActivity.this, Durchfuehrung.class);
                i.putExtra("lessonID", lessonID);
                String comments = checkliste.getText().toString();
                i.putExtra("comments", comments);
                startActivity(i);
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}