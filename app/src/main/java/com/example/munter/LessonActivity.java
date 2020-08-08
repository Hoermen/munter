package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import core.DBHandler;
import core.Lesson;
import core.PlanEntry;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        Intent i = getIntent();
        final String lessonID = i.getStringExtra("lessonID");

        DBHandler db = new DBHandler(getApplicationContext());
        Lesson lesson = db.getLesson(Integer.parseInt(lessonID));
        PlanEntry[] planEntry = db.getPlanentry(lessonID);

        final TextView lessonText = (TextView) findViewById(R.id.lessonText);
        String html = "<h2>"+lesson.getTitle()+"</h2>";
        lessonText.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        //buttonStart
        Button ButtonStart = (Button) findViewById(R.id.ButtonStart);
        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(LessonActivity.this, Durchfuehrung.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
            }
        });
    }
}