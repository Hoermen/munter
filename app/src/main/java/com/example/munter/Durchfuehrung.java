package com.example.munter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import core.DBHandler;
import core.DrawView;
import core.Gesten;
import core.Lesson;
import core.PlanEntry;
import core.Resource;

public class Durchfuehrung extends AppCompatActivity {
    DrawView drawView;
    DBHandler db;
    CountDownTimer mCountDownTimer;
    ProgressBar pb;
    int progress = -1;
    private Handler handler = new Handler();
    int length;
    int id = 0;
    String lessonID;
    long[] time;
    long[] startTime;
    long[] endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durchfuehrung);
        db = new DBHandler(getApplicationContext());
        final EditText notes = (EditText) findViewById(R.id.editTextNotes);

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Intent i = getIntent();
        String comments = i.getStringExtra("comments");
        notes.setText(comments);
        lessonID = i.getStringExtra("lessonID");
        final TextView current = (TextView) findViewById(R.id.current);
        final TextView previous = (TextView) findViewById(R.id.previous);
        final TextView next = (TextView) findViewById(R.id.next);
        final Lesson lesson = db.getLesson(Integer.parseInt(lessonID));
        final PlanEntry[] planEntry = db.getPlanentry(lessonID);
        final Resource[] resource = db.getResource();

        TextView titleStunde = findViewById(R.id.titleStunde);
        String titleText = "<h1>"+lesson.getTitle()+"</h1>";
        titleStunde.setText(HtmlCompat.fromHtml(titleText, HtmlCompat.FROM_HTML_MODE_LEGACY));

        LinearLayout gridLayout = findViewById(R.id.duringLesson);

        gridLayout.setOnTouchListener(new Gesten(this) {
            public boolean onSwipeTop() {
                //es wurde nach oben gewischt, hier den Code einfügen
                return false;
            }
            public boolean onSwipeRight() {
                PlanEntry[] planEntry = db.getPlanentry(lessonID);
                for (int i = 0; i < planEntry.length; i++) {
                    if (startTime[i] != 0) {
                        endTime[i] = System.currentTimeMillis();
                        time[i] = (time[i] + (endTime[i] - startTime[i]));
                        endTime[i] = 0;
                        startTime[i] = 0;
                        id = i;
                    }
                }
                startTime[id-1] = System.currentTimeMillis();

                changeColor(lessonID, id-1);
                TextView value2TV = findViewById(id);
                value2TV.setBackgroundColor(Color.GREEN);
                value2TV.setClickable(false);
                String html = "<h2>"+planEntry[id-1].getTitle()+"</h2><p>"+planEntry[id-1].getComments()+"</p";
                current.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));
                current.setText(""+time[id-1]/1000);
                if (id - 2 >= 0) {
                    previous.setText(planEntry[id - 1].getTitle());
                } else if (Integer.parseInt(lessonID)-1 >= 1) {
                    String letzteStunde = db.getLesson(Integer.parseInt(lessonID)-1).getTitle();
                    previous.setText(letzteStunde);
                } else previous.setText("letzte Stunde nicht verfügbar");
                if (id <= planEntry.length-1) {
                    next.setText(planEntry[id].getTitle());
                } else next.setText("letzte Phase erreicht");
                return false;
            }
            public boolean onSwipeLeft() {
                PlanEntry[] planEntry = db.getPlanentry(lessonID);
                for (int i = 0; i < planEntry.length; i++) {
                    if (startTime[i] != 0) {
                        endTime[i] = System.currentTimeMillis();
                        time[i] = (time[i] + (endTime[i] - startTime[i]));
                        endTime[i] = 0;
                        startTime[i] = 0;
                        id = i;
                    }
                }
                startTime[id+1] = System.currentTimeMillis();

                changeColor(lessonID, id+1);
                TextView value2TV = findViewById(id+2);
                value2TV.setBackgroundColor(Color.GREEN);
                value2TV.setClickable(false);
                String html = "<h2>"+planEntry[id+1].getTitle()+"</h2><p>"+planEntry[id+1].getComments()+"</p";
                current.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));
                current.setText(""+time[id+1]/1000);
                if (id +1 >= 0) {
                    previous.setText(planEntry[id].getTitle());
                } else if (Integer.parseInt(lessonID)-1 >= 1) {
                    String letzteStunde = db.getLesson(Integer.parseInt(lessonID)-1).getTitle();
                    previous.setText(letzteStunde);
                } else previous.setText("letzte Stunde nicht verfügbar");
                if (id + 2 <= planEntry.length-1) {
                    next.setText(planEntry[id + 2].getTitle());
                } else next.setText("letzte Phase erreicht");

                return false;
            }
            public boolean onSwipeBottom() {
                //es wurde nach unten gewischt, hier den Code einfügen
                return false;
            }
            public boolean nichts(){
                //es wurde keine wischrichtung erkannt, hier den Code einfügen
                return false;
            }
        });



        startTime = new long[planEntry.length];
        endTime = new long[planEntry.length];
        time = new long[planEntry.length];
        for (int j = 0; j < planEntry.length; j++) {
            endTime[j] = 0;
            startTime[j] = 0;
        }
        startTime[0] = System.currentTimeMillis();

        if (Integer.parseInt(lessonID)-1 >= 1) {
        String letzteStunde = db.getLesson(Integer.parseInt(lessonID)-1).getTitle();
            previous.setText(letzteStunde);
        }

        current.setText(planEntry[0].getTitle());
        next.setText(planEntry[1].getTitle());


        length = lesson.getLength();
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(length*60);

        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progress < length*60) {
                    progress += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pb.setProgress(progress);
                        }
                    });
                    try {
                        // Sleep for 60 seconds.
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for (int j = 0; j < planEntry.length; j++) {
            final TextView value2TV = new TextView(Durchfuehrung.this);

            final int start = planEntry[j].getStart();
            int length = planEntry[j].getLength();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            value2TV.setWidth(width/lesson.getLength()*length);

            String lessonText = "<h3>"+planEntry[j].getTitle()+"</h3>";
            value2TV.setText(HtmlCompat.fromHtml(lessonText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            value2TV.setId(planEntry[j].getId());
            value2TV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            value2TV.setBackground(getDrawable(R.drawable.border));
            value2TV.setTextColor(getColor(R.color.colorText));
            value2TV.setGravity(Gravity.CENTER);

            final int finalJ = j;
            id = j;
            value2TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < planEntry.length; i++) {
                        if (startTime[i] != 0) {
                            endTime[i] = System.currentTimeMillis();
                            time[i] = (time[i] + (endTime[i] - startTime[i]));
                            endTime[i] = 0;
                            startTime[i] = 0;
                        }
                    }

                    startTime[finalJ] = System.currentTimeMillis();

                    changeColor(lessonID, value2TV.getId());
                    value2TV.setBackgroundColor(Color.GREEN);
                    value2TV.setClickable(false);
                    String html = "<h2>"+planEntry[finalJ].getTitle()+"</h2><p>"+planEntry[finalJ].getComments()+"</p";
                    current.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    current.setText(""+time[finalJ]/1000);
                    if (finalJ - 1 >= 0) {
                        previous.setText(planEntry[finalJ - 1].getTitle());
                    } else if (Integer.parseInt(lessonID)-1 >= 1) {
                            String letzteStunde = db.getLesson(Integer.parseInt(lessonID)-1).getTitle();
                            previous.setText(letzteStunde);
                        } else previous.setText("letzte Stunde nicht verfügbar");
                    if (finalJ + 1 <= planEntry.length-1) {
                        next.setText(planEntry[finalJ + 1].getTitle());
                    } else next.setText("letzte Phase erreicht");
                }
            });

            int padding_in_dp = 5;
            float scala = getResources().getDisplayMetrics().density;
            int padding_in_px = (int) (padding_in_dp * scala + 0.5f);

           // value2TV.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);

            LinearLayout ll = (LinearLayout) findViewById(R.id.PlanEntry);
            ll.addView(value2TV);
        }

        TextView entry = findViewById(planEntry[0].getId());
        entry.setBackgroundColor(Color.GREEN);
        entry.setClickable(false);

        //buttonStart
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                new AlertDialog.Builder(Durchfuehrung.this)
                        .setTitle("Stunde beenden")
                        .setMessage("Wollem Sie die Stunde wirklich beenden?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < planEntry.length; i++) {
                                    if (startTime[i] != 0) {
                                        endTime[i] = System.currentTimeMillis();
                                        time[i] = (time[i] + (endTime[i] - startTime[i]));
                                        endTime[i] = 0;
                                        startTime[i] = 0;
                                    }
                                }
                                Intent i = new Intent(Durchfuehrung.this, FeedbackActivity.class);
                                i.putExtra("lessonID", lessonID);

                                for (int k = 0; k < planEntry.length; k++) {
                                    i.putExtra(Integer.toString(k), ""+time[k]/1000);
                                }
                                String comments = notes.getText().toString();
                                i.putExtra("comments", comments);
                                startActivity(i);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("nein", null)
                        .show();


            }
        });
        final TextView time = (TextView) findViewById(R.id.Chronometer);
        Button timer = (Button) findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                final EditText input = new EditText(Durchfuehrung.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                final NumberPicker np = new NumberPicker(Durchfuehrung.this);
                np.setMinValue(1);
                np.setMaxValue(90);
                np.setValue(5);


                new AlertDialog.Builder(Durchfuehrung.this)
                        .setTitle("Timer starten")
                        .setMessage("Minuten wählen")
                        .setView(np)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Timer starten", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final long zeit = np.getValue();

                                //cancel the old countDownTimer
                                if(mCountDownTimer!=null){
                                    mCountDownTimer.cancel();
                                }
                                mCountDownTimer = new CountDownTimer(zeit*1000*60,1000){
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
                                        time.setText("Ende!");
                                        time.setTextColor(Color.RED);
                                    }
                                };
                                   mCountDownTimer.start();
                                }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Abbrechen", null)
                        .show();
            }
        });
    }

    private void changeColor(String lessonID, int id) {
        PlanEntry[] pe = db.getPlanentry(lessonID);
        TextView[] tv = new TextView[pe.length];
        for (int j = 0; j < pe.length; j++) {
            tv[j] = findViewById(pe[j].getId());
            tv[j].setBackground(getDrawable(R.drawable.border));
            tv[j].setClickable(true);
        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}