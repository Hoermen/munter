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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import core.DBHandler;
import core.Lesson;
import core.PlanEntry;
import core.Resource;
import core.Sequence;

public class Durchfuehrung extends AppCompatActivity {
    DBHandler db;
    CountDownTimer mCountDownTimer;
    ProgressBar pb;
    int progress = -1;
    private Handler handler = new Handler();
    int length;

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

        length = neu.getLength();
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

            int start = planEntry[j].getStart();
            int length = planEntry[j].getLength();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            value2TV.setWidth(width/neu.getLength()*length);

            String lessonText = "<h3>"+planEntry[j].getTitle()+"</h3>";
            value2TV.setText(HtmlCompat.fromHtml(lessonText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            value2TV.setId(planEntry[j].getId());
            value2TV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            value2TV.setBackground(getDrawable(R.drawable.border));
            value2TV.setTextColor(getColor(R.color.colorText));

            int padding_in_dp = 5;
            float scala = getResources().getDisplayMetrics().density;
            int padding_in_px = (int) (padding_in_dp * scala + 0.5f);

           // value2TV.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);

            LinearLayout ll = (LinearLayout) findViewById(R.id.PlanEntry);
            ll.addView(value2TV);
        }

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
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}