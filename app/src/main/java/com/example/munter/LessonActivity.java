package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import core.DBHandler;
import core.Lesson;
import core.Resource;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);
        Intent i = getIntent();
        final String lessonID = i.getStringExtra("lessonID");

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        DBHandler db = new DBHandler(getApplicationContext());
        Lesson lesson = db.getLesson(Integer.parseInt(lessonID));
        Resource[] resource = db.getResource(Integer.parseInt(lessonID));

        String FILENAME = "Unbenannt.PNG";
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myFile = new File(folder, FILENAME);


        String mat = "<h4>Materialien:</h4>";
        for (int j = 0; j < resource.length; j++) {
            mat=mat+"<p><font color=\"black\"><a href=\""+resource[j].getTextContent()+"\">"+resource[j].getTitle()+" ("+resource[j].getFilename()+")</a></font></p>";
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
        String html3 = "<h4>Notizen (lange Tippen zum Zeichnen):</h4>";
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

        final LinearLayout ausblick = findViewById(R.id.Ausblick);
        checkliste.setText(lesson.getComments());


        ausblick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(LessonActivity.this, drawActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
                return false;
            }
        });
        checkliste.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(LessonActivity.this, drawActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
                return false;
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