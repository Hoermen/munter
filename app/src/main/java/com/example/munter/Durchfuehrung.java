package com.example.munter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.stephenvinouze.materialnumberpickercore.MaterialNumberPicker;

import core.DBHandler;
import core.Lesson;
import core.PlanEntry;
import core.Resource;
import core.Sequence;

public class Durchfuehrung extends AppCompatActivity {
    DBHandler db;
    Thread thread;

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
        final TextView time = (TextView) findViewById(R.id.Chronometer);
        Button timer = (Button) findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                final EditText input = new EditText(Durchfuehrung.this);
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                new AlertDialog.Builder(Durchfuehrung.this)
                        .setTitle("Timer starten")
                        .setMessage("Minuten wählen")
                        .setView(input)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Timer starten", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final long zeit = Long.parseLong(input.getText().toString());
                                new CountDownTimer(zeit*1000*60,1000){
                                    public void onTick(long millisUntilFinished) {
                                        long minutes = (millisUntilFinished / 1000) / 60;
                                        long seconds = (millisUntilFinished / 1000) % 60;

                                        String timeString = String.format("%02d:%02d", minutes, seconds);
                                        time.setText(timeString);
                                    }

                                    public void onFinish() {
                                        ObjectAnimator anim = ObjectAnimator.ofInt(time, "backgroundColor", Color.WHITE, Color.RED,
                                                Color.WHITE);
                                        anim.setDuration(1500);
                                        anim.setEvaluator(new ArgbEvaluator());
                                        anim.setRepeatMode(ValueAnimator.REVERSE);
                                        anim.setRepeatCount(10);
                                        anim.start();
                                    }
                                }.start();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Abbrechen", null)
                        .show();


            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}