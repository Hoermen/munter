package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import core.DBHandler;
import core.Lesson;
import core.PlanEntry;
import core.Resource;
import core.ResourceType;
import core.Sequence;

public class MainActivity extends AppCompatActivity {

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView munter = findViewById(R.id.munter);
        String munterText = "<h1>munter</h1>";
        munter.setText(HtmlCompat.fromHtml(munterText, HtmlCompat.FROM_HTML_MODE_LEGACY));

        ImageView icon = findViewById(R.id.icon);
        icon.setImageResource(R.mipmap.munter);

        TextView munter2 = findViewById(R.id.munter2);
        String munter2Text = "<h3>- DIE Unterstützung für guten Unterricht -</h3>";
        munter2.setText(HtmlCompat.fromHtml(munter2Text, HtmlCompat.FROM_HTML_MODE_LEGACY));


        //buttonLogin
        Button ButtonSkip = (Button) findViewById(R.id.buttonSkip);
        ButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, SequenceActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //buttonLogin
        Button ButtonNew = (Button) findViewById(R.id.buttonNew);
        ButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, PlatonLoginActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        db = new DBHandler(getApplicationContext());
        SQLiteDatabase sql = db.getWritableDatabase();
        db.onUpgrade(sql, 2,3);
      createModel();
    }

    @Override
    protected void onDestroy() {
        db.closeDB();
        super.onDestroy();
    }
    public void createModel() {
        Sequence sequence1 = new Sequence();
        sequence1.setTitle("anders");
        sequence1.setComments("viel Sprechen");
        sequence1.setGoal("interessanter");
        sequence1.setGrade(8);
        sequence1.setSubject("Deutsch");
        sequence1.setId(8);

        Sequence sequence2 = new Sequence();
        sequence2.setTitle("Gleichungen");
        sequence2.setComments("viel Sprechen");
        sequence2.setGoal("interessant");
        sequence2.setGrade(9);
        sequence2.setSubject("Mathe");
        sequence2.setId(1);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Bilder");
        lesson1.setId(1);
        lesson1.setComments("aufpassen");
        lesson1.setGoal("vertiefen");
        lesson1.setHomeworks("Aufsatz");
        lesson1.setOrder(1);
        lesson1.setLength(90);
        lesson1.setSequenceid(1);
        lesson1.setBeschreibung("Beschreibung hier einfügen");

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test");
        lesson2.setId(2);
        lesson2.setComments("test ist wichtig");
        lesson2.setGoal("neues Lernen");
        lesson2.setHomeworks("Übungsaufgaben");
        lesson2.setOrder(1);
        lesson2.setLength(90);
        lesson2.setSequenceid(8);

        PlanEntry planEntry1 = new PlanEntry();
        planEntry1.setColor("blue");
        planEntry1.setComments("1. Kommentar");
        planEntry1.setGoal("Einführung");
        planEntry1.setId(1);
        planEntry1.setLength(10);
        planEntry1.setLessonId(1);
        planEntry1.setSocialForm("Gruppenarbeit");
        planEntry1.setStart(0);
        planEntry1.setTitle("Anfang");
        planEntry1.setTrack(1);
        planEntry1.setSteps("Schritt 1");

        PlanEntry planEntry2 = new PlanEntry();
        planEntry2.setColor("blue");
        planEntry2.setComments("2. Kommentar");
        planEntry2.setGoal("Aufgaben");
        planEntry2.setId(2);
        planEntry2.setLength(80);
        planEntry2.setLessonId(1);
        planEntry2.setSocialForm("Einzelarbeit");
        planEntry2.setStart(10);
        planEntry2.setTitle("Danach");
        planEntry2.setTrack(1);
        planEntry2.setSteps("Schritt 2");

        Resource resource1 = new Resource();
        resource1.setId(1);
        resource1.setFilename("text.txt");
        resource1.setTextContent("text als file to string");
        resource1.setTitle("Beispiel");
        resource1.setType(ResourceType.BLACKBOARD);
        resource1.setLessonid(1);
        resource1.setPlanentryid(1);

        Resource resource2 = new Resource();
        resource2.setId(2);
        resource2.setFilename("guck.txt");
        resource2.setTextContent("film");
        resource2.setTitle("test");
        resource2.setType(ResourceType.BLACKBOARD);

        PlanEntry planEntry8 = new PlanEntry();
        planEntry8.setColor("blue");
        planEntry8.setComments("1. Kommentar");
        planEntry8.setGoal("Einführung");
        planEntry8.setId(3);
        planEntry8.setLength(10);
        planEntry8.setLessonId(2);
        planEntry8.setSocialForm("Gruppenarbeit");
        planEntry8.setStart(0);
        planEntry8.setTitle("neu");
        planEntry8.setTrack(1);
        planEntry8.setSteps("Schritt 1");

        PlanEntry planEntry9 = new PlanEntry();
        planEntry9.setColor("blue");
        planEntry9.setComments("4Kommentar");
        planEntry9.setGoal("Einführung");
        planEntry9.setId(4);
        planEntry9.setLength(10);
        planEntry9.setLessonId(2);
        planEntry9.setSocialForm("Gruppenarbeit");
        planEntry9.setStart(0);
        planEntry9.setTitle("schluss");
        planEntry9.setTrack(1);
        planEntry9.setSteps("Schritt 1");

        DBHandler db = new DBHandler(getApplicationContext());
        db.createLesson(lesson1);
        db.createLesson(lesson2);
        db.createSequence(sequence1);
        db.createSequence(sequence2);
        db.createPlanentry(planEntry1);
        db.createPlanentry(planEntry2);
        db.createPlanentry(planEntry8);
        db.createPlanentry(planEntry9);
        db.createResource(resource1);
        db.createResource(resource2);

    }
}