package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import core.CustomField;
import core.CustomFieldDefinition;
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
        db.onUpgrade(sql, 1,2);
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

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test");
        lesson2.setId(5);
        lesson2.setComments("test ist wichtig");
        lesson2.setGoal("neues Lernen");
        lesson2.setHomeworks("Übungsaufgaben");
        lesson2.setOrder(1);
        lesson2.setLength(90);
        lesson2.setSequenceid(1);

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
        planEntry2.setLength(15);
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

        Resource resource2 = new Resource();
        resource2.setId(2);
        resource2.setFilename("guck.txt");
        resource2.setTextContent("film");
        resource2.setTitle("test");
        resource2.setType(ResourceType.BLACKBOARD);

        DBHandler db = new DBHandler(getApplicationContext());
        db.createLesson(lesson1);
        db.createLesson(lesson2);
        db.createSequence(sequence1);
        db.createSequence(sequence2);
        db.createPlanentry(planEntry1);
        db.createPlanentry(planEntry2);
        db.createResource(resource1);
        db.createResource(resource2);

    }
}