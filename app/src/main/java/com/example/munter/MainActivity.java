package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import core.DBHandler;
import core.Lesson;

public class MainActivity extends AppCompatActivity {

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(getApplicationContext());

        Lesson lesson = new Lesson();
        lesson.setTitle("Test");
        lesson.setId(2);
        lesson.setComments("test ist wichtig");
        lesson.setGoal("neues Lernen");
        lesson.setHomeworks("Übungsaufgaben");
        lesson.setOrder(1);
        lesson.setLength(90);
        lesson.setSequenceid(1);

        final TextView tv = (TextView) findViewById(R.id.title);
        tv.setText("alt");
        SQLiteDatabase sql = db.getWritableDatabase();


        db.onUpgrade(sql, 1, 2);
        db.createLesson(lesson);

        final Lesson neu = db.getLesson();

        final Button button = findViewById(R.id.exit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv.setText(neu.getTitle());
            }
        });

    }

    @Override
    protected void onDestroy() {
        db.closeDB();
        super.onDestroy();
    }


}