package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "";
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();

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
        lesson1.setTitle("erste super neue Stunde");
        lesson1.setId(1);
        lesson1.setComments("aufpassen");
        lesson1.setGoal("vertiefen");
        lesson1.setHomeworks("Aufsatz");
        lesson1.setOrder(1);
        lesson1.setLength(90);
        lesson1.setSequenceid(1);
        lesson1.setBeschreibung("Beschreibung hier einfügen");
        lesson1.setFeedback("1. Phase ändern");

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test");
        lesson2.setId(2);
        lesson2.setComments("test ist wichtig");
        lesson2.setGoal("neues Lernen");
        lesson2.setHomeworks("Übungsaufgaben");
        lesson2.setOrder(1);
        lesson2.setLength(90);
        lesson2.setSequenceid(8);
        lesson2.setFeedback("alles super");

        PlanEntry planEntry1 = new PlanEntry();
        planEntry1.setColor("blue");
        planEntry1.setComments("1. Kommentafdsggffffffgggggggggggggggggdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggr");
        planEntry1.setGoal("Einführung");
        planEntry1.setId(1);
        planEntry1.setLength(10);
        planEntry1.setLessonId(1);
        planEntry1.setSocialForm("Gruppenarbeit");
        planEntry1.setStart(0);
        planEntry1.setTitle("Erste richtig wichtige Phase");
        planEntry1.setReserve("Aufgaben für schnelle");
        planEntry1.setBeschreibung("so sollte der Unterricht laufen");
        planEntry1.setReallength(660);

        PlanEntry planEntry2 = new PlanEntry();
        planEntry2.setColor("blue");
        planEntry2.setComments("2. Kommentar");
        planEntry2.setGoal("Aufgaben");
        planEntry2.setId(2);
        planEntry2.setLength(20);
        planEntry2.setLessonId(1);
        planEntry2.setSocialForm("Einzelarbeit");
        planEntry2.setStart(10);
        planEntry2.setTitle("zweite");
        planEntry2.setReallength(1500);

        PlanEntry planEntry5 = new PlanEntry();
        planEntry5.setColor("blue");
        planEntry5.setComments("1. Kommentar");
        planEntry5.setGoal("Einführung");
        planEntry5.setId(5);
        planEntry5.setLength(30);
        planEntry5.setLessonId(1);
        planEntry5.setSocialForm("Gruppenarbeit");
        planEntry5.setStart(30);
        planEntry5.setTitle("dritte");
        planEntry5.setReallength(2000);

        PlanEntry planEntry6 = new PlanEntry();
        planEntry6.setColor("blue");
        planEntry6.setComments("2. Kommentar");
        planEntry6.setGoal("Aufgaben");
        planEntry6.setId(6);
        planEntry6.setLength(30);
        planEntry6.setLessonId(1);
        planEntry6.setSocialForm("Einzelarbeit");
        planEntry6.setStart(60);
        planEntry6.setTitle("vierte");
        planEntry6.setReallength(2000);

        Resource resource1 = new Resource();
        resource1.setId(1);
        resource1.setFilename("Unbenannt.PNG");
        resource1.setTextContent("file:/storage/emulated/0/Download/Unbenannt.PNG");
        //resource1.setTextContent("file:/sdcard/Download/Unbenannt.PNG");
        resource1.setTitle("Bild");
        resource1.setType("image/*");
        resource1.setLessonid(1);
        resource1.setPlanentryid(1);

        Resource resource2 = new Resource();
        resource2.setId(2);
        resource2.setFilename("test.pdf");
        resource2.setTextContent("file:/storage/emulated/0/Download/test.pdf");
        //resource2.setTextContent("file:/sdcard/Download/test.pdf");
        resource2.setTitle("Plan");
        resource2.setType("application/pdf");
        resource2.setLessonid(1);
        resource2.setPlanentryid(0);

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

        PlanEntry planEntry9 = new PlanEntry();
        planEntry9.setColor("blue");
        planEntry9.setComments("4Kommentar");
        planEntry9.setGoal("Einführung");
        planEntry9.setId(4);
        planEntry9.setLength(10);
        planEntry9.setLessonId(2);
        planEntry9.setSocialForm("Gruppenarbeit");
        planEntry9.setStart(10);
        planEntry9.setTitle("schluss");

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
        db.createPlanentry(planEntry5);
        db.createPlanentry(planEntry6);
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}