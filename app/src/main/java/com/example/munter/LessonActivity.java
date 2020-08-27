package com.example.munter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.pdfview.PDFView;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import core.DBHandler;
import core.Lesson;
import core.PlanEntry;
import core.Resource;

public class LessonActivity extends AppCompatActivity {
    SpannableStringBuilder spanTxt;

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
        final Resource[] resource = db.getResource(Integer.parseInt(lessonID));
        final PlanEntry[] planEntries = db.getPlanentry(lessonID);

        spanTxt = new SpannableStringBuilder("Materialien: \n\n");
        for (int j = 0; j < resource.length; j++) {
            spanTxt.append(resource[j].getFilename()+"\n");
            final int finalJ = j;
            spanTxt.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    if (resource[finalJ].getType().contains("image/*")) {
                        Intent i = new Intent(LessonActivity.this, materialActivity.class);
                        i.putExtra("material", resource[finalJ].getTextContent());
                        startActivity(i);
                    }
                    if (resource[finalJ].getType().contains("application/pdf")) {
                        Intent i = new Intent(LessonActivity.this, pdfActivity.class);
                        i.putExtra("material", resource[finalJ].getTextContent());
                        startActivity(i);
                    }
                }
            }, spanTxt.length() - resource[j].getFilename().length()-1, spanTxt.length(), 0);
        }

        final TextView lessonText = (TextView) findViewById(R.id.LessonInfo);
        String html = "<h2><u>"+lesson.getTitle()+"</u></h2><p>Beschreibung: "+lesson.getBeschreibung()+"</p>";
        lessonText.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final TextView HAText = (TextView) findViewById(R.id.HAInfo);
        String html2 = "<h4>Hausaufgaben:</h4><p>"+lesson.getHomeworks()+"</p>";
        HAText.setText(HtmlCompat.fromHtml(html2, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final TextView materialien = findViewById(R.id.LessonMaterialien);
        materialien.setMovementMethod(LinkMovementMethod.getInstance());
        materialien.setText(spanTxt, TextView.BufferType.SPANNABLE);

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

        LinearLayout tableLayout = findViewById(R.id.Zeitplan);
        int k = 0;
        int index=0;
        // Initialize maximum element
        int max = 0;

        // Traverse array elements from second and
        // compare every element with current max
        for (index = 0; index < planEntries.length; index++) {
            if (planEntries[index].getGroupid() == 0) {
                if (planEntries[index].getTrack() > max)
                    max = planEntries[index].getTrack();
            }
            if (planEntries[index].getTrack() == 1) {
                k=k+1;
            }
        }

        TextView tv = findViewById(R.id.textView3);
        Random random = new Random();

        TableLayout table = new TableLayout(LessonActivity.this);
        for (int l=0; l < k+1; l++) {
            TableRow row = new TableRow(LessonActivity.this);
            for (int j=0; j < max+1; j++) {
                for (int x=0; x < planEntries.length; x++) {
                    if (planEntries[x].getGroupid() == 0) {
                        if (planEntries[x].getReihe() == l){
                            if (planEntries[x].getTrack() == j) {
                                TextView tv2 = new TextView(LessonActivity.this);
                                String plan = "<h3>Titel: " + planEntries[x].getTitle() + "</h3><p>Länge: "+planEntries[x].getLength()+"</p><p>Ziele: "+
                                        planEntries[x].getGoal()+"</p><p>Sozialform: "+planEntries[x].getSocialForm()+"</p><p>Kommentare: "+planEntries[x].getComments()+"</p>";
                                tv2.setText(HtmlCompat.fromHtml(plan, HtmlCompat.FROM_HTML_MODE_LEGACY));
                                tv2.setBackground(getDrawable(R.drawable.border));
                                Display display = getWindowManager().getDefaultDisplay();
                                Point size = new Point();
                                display.getSize(size);
                                int width = size.x;
                                int height = size.y;
                                tv2.setWidth(width / (max+1));
                                tv2.setHeight(height / 3);
                                int padding_in_dp = 5;
                                final float scale = getResources().getDisplayMetrics().density;
                                int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

                                tv2.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
                                row.addView(tv2);
                            }
                        }
                    }
                }
            }
            table.addView(row);
        }
        tableLayout.addView(table);



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